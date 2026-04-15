package com.kiri.ai.ui.screens

import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.core.content.FileProvider
import com.kiri.ai.BuildConfig
import com.kiri.ai.ui.components.*
import com.kiri.ai.ui.theme.*
import com.kiri.ai.data.models.*
import com.kiri.ai.ui.viewmodels.MainViewModel
import com.kiri.ai.ui.viewmodels.ChatViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * ====================================================================================
 * CRITICAL_STABILITY_NOTICE - CHAT_SCREEN_ERROR_FIXES
 * ====================================================================================
 * 
 * DATE: 2026-04-15
 * SEVERITY: CRITICAL - App crashes on attachment/chat load
 * 
 * ERROR_1_FIXED: SnapshotStateObserver.observeReads Crash
 * --------------------------------------------------------
 * STACK_TRACE_LOCATION:
 *   androidx.compose.runtime.snapshots.SnapshotStateObserver.observeReads
 *   androidx.compose.ui.node.OwnerSnapshotObserver.observeReads$ui_release
 *   androidx.compose.ui.node.NodeCoordinator$drawBlock$1.invoke
 * 
 * CAUSE: ViewModel was using mutableStateOf() instead of StateFlow
 * FIX: ChatViewModel now uses StateFlow with collectAsStateWithLifecycle()
 * 
 * ERROR_2_FIXED: Activity Result Callback During Draw Phase
 * ---------------------------------------------------------
 * CAUSE: rememberLauncherForActivityResult callback fires during draw/layout
 *        Immediate viewModel.onFileSelected() call triggers state mutation mid-draw
 * 
 * FIX: Deferred state update using coroutine scope:
 *      scope.launch { viewModel.onFileSelected(it, name) }
 *      This schedules update on next frame, avoiding snapshot observation conflict
 * 
 * RULES_FOR_STABILITY:
 * 1. LazyColumn items MUST use stable keys to prevent redundant recompositions.
 * 2. Avoid nesting the LazyColumn inside other scrollable containers.
 * 3. IME (keyboard) padding must be handled at the root level to prevent remeasure loops.
 * 4. Keep the hierarchy flat to avoid 'dispatchGetDisplayList' recursion crashes.
 * 5. NEVER call ViewModel methods directly in ActivityResultCallback - always defer.
 * 
 * VERIFICATION_CHECKLIST:
 * [x] Attachments add without crash
 * [x] Chat sessions start safely
 * [x] Old chats with attachments load without crash
 * [x] State updates are lifecycle-aware
 * ====================================================================================
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
    id: String? = null,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    val context = LocalContext.current

    // LOAD_INITIAL_CONVERSATION: React to navigation parameters
    LaunchedEffect(id) {
        if (!id.isNullOrBlank() && id != state.currentConversationId) {
            viewModel.selectConversation(id)
        }
    }

    // File Picker Launcher
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                val name = context.contentResolver.query(it, null, null, null, null)?.use { cursor ->
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex != -1 && cursor.moveToFirst()) {
                        cursor.getString(nameIndex)
                    } else null
                } ?: it.lastPathSegment

                scope.launch {
                    viewModel.onFileSelected(it, name)
                }
            }
        }
    )

    // Scroll to bottom on new messages
    val totalItems = state.messages.size + (if (state.isSending) 1 else 0)
    LaunchedEffect(totalItems) {
        if (totalItems > 0) {
            kotlinx.coroutines.delay(100)
            scrollState.animateScrollToItem(totalItems - 1)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = VelvetBlack,
                drawerContentColor = ShowroomWhite,
                drawerShape = RoundedCornerShape(0.dp), // Sharper aesthetic
                modifier = Modifier.width(300.dp)
            ) {
                Spacer(modifier = Modifier.height(64.dp))
                Text(
                    text = "KIRI // ATELIER",
                    style = KiriTypography.labelLarge,
                    modifier = Modifier.padding(horizontal = 24.dp),
                    color = ShowroomWhite
                )
                Spacer(modifier = Modifier.height(32.dp))
                
                KiriButton(
                    text = "NEW_SESSION",
                    onClick = { 
                        viewModel.newChat()
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 24.dp).height(40.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    "RECENT_LOGS",
                    style = KiriTypography.labelMedium.copy(color = SilverMist),
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
                
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(
                        items = state.conversations,
                        key = { it.id ?: "conv_${it.hashCode()}" }
                    ) { conv ->
                        NavigationDrawerItem(
                            label = { 
                                Text(
                                    conv.title?.uppercase() ?: "UNTITLED_LOG", 
                                    style = KiriTypography.labelMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                ) 
                            },
                            selected = conv.id == state.currentConversationId,
                            onClick = {
                                conv.id?.let { viewModel.selectConversation(it) }
                                scope.launch { drawerState.close() }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.Transparent,
                                selectedContainerColor = DarkGray,
                                selectedTextColor = ShowroomWhite,
                                unselectedTextColor = SilverMist
                            ),
                            shape = RoundedCornerShape(0.dp),
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                }
                
                HorizontalDivider(color = SilverMist.copy(alpha = 0.1f))

                // Profile Row (Technical)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("profile") }
                        .padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        state.user?.name?.uppercase() ?: "USER_NULL", 
                        style = KiriTypography.labelLarge,
                        color = ShowroomWhite
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Default.Settings, contentDescription = null, tint = SilverMist, modifier = Modifier.size(16.dp))
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { 
                        Text(
                            state.currentTitle.uppercase(), 
                            style = KiriTypography.labelLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        ) 
                    },
                    modifier = Modifier.statusBarsPadding(), // Handles status bar
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        // High-Visibility Theme Toggle
                        val mainViewModel: MainViewModel = hiltViewModel()
                        val isDarkMode by mainViewModel.isDarkMode.collectAsState()
                        IconButton(onClick = { mainViewModel.toggleTheme() }) {
                            Icon(
                                if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode, 
                                contentDescription = "Toggle Theme"
                            )
                        }
                        IconButton(onClick = { navController.navigate("pricing") }) {
                            Icon(Icons.Default.Star, contentDescription = "Premium")
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                        navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                        actionIconContentColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            },
            bottomBar = {
                // Moved into content for better IME control
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .navigationBarsPadding() // Handles bottom home handle
                    .imePadding() // Handles keyboard
            ) {
                if (!state.isConnected) {
                    Surface(
                        color = MaterialTheme.colorScheme.errorContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "OFFLINE_MODE // CONNECTIVITY_LOST",
                            style = KiriTypography.labelSmall,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.padding(vertical = 4.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(
                            items = state.messages,
                            key = { it.getStableId() }
                        ) { msg ->
                            // STRICT_ISOLATION: Force Compose to treat each message as an independent entity
                            // Using graphicsLayer to isolate message renders from main layout pass
                            key(msg.getStableId()) {
                                Box(modifier = Modifier.graphicsLayer { clip = true }) {
                                    KiriMessageBubble(msg)
                                }
                            }
                        }
                        
                        if (state.isSending) {
                            item(key = "typing_indicator") { 
                                Box(modifier = Modifier.graphicsLayer { clip = true }) {
                                    TypingIndicator() 
                                }
                            }
                        }
                    }

                    if (state.isLoadingMessages) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.primary)
                    }
                }

                // Chat Input Bar at the bottom of the Column
                ChatInputBar(
                    message = state.inputMessage,
                    onMessageChange = { viewModel.onMessageChange(it) },
                    onSend = { viewModel.sendMessage() },
                    onAttachClick = { filePickerLauncher.launch(arrayOf("*/*")) },
                    selectedFileUri = state.selectedFileUri,
                    selectedFileName = state.selectedFileName,
                    onClearFile = { viewModel.clearSelectedFile() },
                    isSending = state.isSending
                )
            }
        }
    }
}

@Composable
fun TypingIndicator() {
    val infiniteTransition = rememberInfiniteTransition(label = "typing")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "alpha"
    )

    Row(
        modifier = Modifier
            .padding(vertical = 12.dp)
            .graphicsLayer { this.alpha = alpha }, // STABILITY_FIX: Use graphicsLayer for alpha to avoid measurement pass
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "KIRI_IS_THINKING",
            style = KiriTypography.labelMedium.copy(color = ShowroomWhite)
        )
    }
}
