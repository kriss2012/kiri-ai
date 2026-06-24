package com.kiriai.kiriorganization.ui.screens

import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.material.icons.automirrored.filled.Chat
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
import com.kiriai.kiriorganization.ui.components.*
import com.kiriai.kiriorganization.ui.theme.*
import com.kiriai.kiriorganization.data.models.*
import com.kiriai.kiriorganization.ui.viewmodels.MainViewModel
import com.kiriai.kiriorganization.ui.viewmodels.ChatViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * Kiri AI - Chat Interface
 * 
 * Cinematic, high-performance messaging interface implementing the Bugatti Design System.
 * Optimized for architectural stability and technical flair.
 */

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
    id: String? = null,
    viewModel: ChatViewModel = hiltViewModel()
) {
    android.util.Log.d("Kiri_DEBUG", "ChatScreen: Navigation triggered with id=$id")
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    val context = LocalContext.current

    // Error Handling - Enhanced to suppress Auth errors handled by navigation
    LaunchedEffect(state.error) {
        state.error?.let {
            // If the error is about tokens/auth, we let the MainActivity's navigation handle it
            if (it.contains("token", ignoreCase = true) || it.contains("expired", ignoreCase = true) || it.contains("401")) {
                android.util.Log.d("Kiri_DEBUG", "ChatScreen: Suppressing auth error toast, redirection expected.")
                return@let
            }

            val userFriendlyMessage = if (it.contains("API_KEY") || it.contains("HTTP_ERROR") || it.contains("SERVER")) {
                "Service is currently optimizing. Please try again in a moment."
            } else {
                it
            }
            Toast.makeText(context, userFriendlyMessage, Toast.LENGTH_LONG).show()
        }
    }

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
            android.util.Log.d("Kiri_DEBUG", "FilePicker: Result received uri=$uri")
            uri?.let {
                val name = try {
                    context.contentResolver.query(it, null, null, null, null)?.use { cursor ->
                        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (nameIndex != -1 && cursor.moveToFirst()) {
                            cursor.getString(nameIndex)
                        } else null
                    }
                } catch (e: Exception) {
                    android.util.Log.e("Kiri_DEBUG", "FilePicker: Query failed", e)
                    null
                } ?: it.lastPathSegment

                android.util.Log.d("Kiri_DEBUG", "FilePicker: Handing over to ViewModel name=$name")
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

    val isDark = LocalThemeMode.current
    
    // THEME_ANIMATION_ENGINE: Smooth transition between light/dark glass tones
    val animatedBgStart by animateColorAsState(
        targetValue = if (isDark) DeepSpaceBlue else Color(0xFFF0F2F5),
        animationSpec = tween(1200, easing = LinearOutSlowInEasing), label = "bgStart"
    )
    val animatedBgEnd by animateColorAsState(
        targetValue = if (isDark) VelvetBlack else Color(0xFFFFFFFF),
        animationSpec = tween(1200, easing = LinearOutSlowInEasing), label = "bgEnd"
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = (if (isDark) VelvetBlack else Color.White).copy(alpha = 0.95f),
                drawerContentColor = MaterialTheme.colorScheme.onSurface,
                drawerShape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
                    .graphicsLayer { clip = true }
                    .border(
                        width = 1.dp, 
                        brush = Brush.horizontalGradient(listOf(Color.Transparent, if (isDark) GlassBorderWhite else GlassBorderBlack.copy(alpha = 0.1f))),
                        shape = RoundedCornerShape(0.dp)
                    )
            ) {
                Spacer(modifier = Modifier.height(64.dp))
                Text(
                    text = "KIRI // ATELIER",
                    style = KiriTypography.labelLarge,
                    modifier = Modifier.padding(horizontal = 24.dp),
                    color = MaterialTheme.colorScheme.onSurface
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
                    "OPERATION_MODES",
                    style = KiriTypography.labelMedium.copy(color = SilverMist),
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )

                NavigationDrawerItem(
                    label = { Text("CORE_CHAT", style = KiriTypography.labelMedium) },
                    selected = true,
                    onClick = { scope.launch { drawerState.close() } },
<<<<<<< Updated upstream
                    icon = { Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(16.dp)) },
=======
                    icon = { Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null, modifier = Modifier.size(18.dp), tint = BrutalistBlack) },
>>>>>>> Stashed changes
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.Transparent,
                        selectedContainerColor = if (isDark) DarkGray else Color.Black.copy(alpha = 0.05f),
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = SilverMist
                    ),
                    shape = RoundedCornerShape(0.dp),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                NavigationDrawerItem(
                    label = { Text("IMAGE_LAB", style = KiriTypography.labelMedium) },
                    selected = false,
                    onClick = { 
                        navController.navigate("imagelab")
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(16.dp)) },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.Transparent,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = SilverMist
                    ),
                    shape = RoundedCornerShape(0.dp),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                NavigationDrawerItem(
                    label = { Text("CODE_SPACE", style = KiriTypography.labelMedium) },
                    selected = false,
                    onClick = { 
                        Toast.makeText(context, "CODE_SPACE_LOCKED", Toast.LENGTH_SHORT).show()
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.Code, contentDescription = null, modifier = Modifier.size(16.dp)) },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.Transparent,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = SilverMist
                    ),
                    shape = RoundedCornerShape(0.dp),
                    modifier = Modifier.padding(horizontal = 12.dp)
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
                                selectedContainerColor = if (isDark) DarkGray else Color.Black.copy(alpha = 0.05f),
                                selectedTextColor = MaterialTheme.colorScheme.primary,
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
                        color = MaterialTheme.colorScheme.onSurface
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
                            style = KiriTypography.labelLarge.copy(letterSpacing = 2.sp),
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
                        val isDarkMode by mainViewModel.isDarkMode.collectAsStateWithLifecycle()
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
                        containerColor = (if (isDark) VelvetBlack else Color.White).copy(alpha = 0.7f),
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                        navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                        actionIconContentColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            },
            bottomBar = {
                // Moved into content for better IME control
            },
            containerColor = Color.Transparent
        ) { padding ->
            val backgroundGradient = Brush.verticalGradient(
                colors = listOf(animatedBgStart, animatedBgEnd)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundGradient)
                    .padding(padding)
                    .imePadding()
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    if (state.messages.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "SYSTEM_READY",
                                style = KiriTypography.labelSmall.copy(color = SilverMist),
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            Text(
                                text = "KIRI AI",
                                style = KiriTypography.headlineLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 8.sp
                                ),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            Text(
                                text = "Multimodal intelligence layer active. Send a message or upload an artifact to begin analysis.",
                                style = KiriTypography.bodyMedium.copy(color = SilverMist),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 48.dp)
                            )
                        }
                    } else {
                        LazyColumn(
                            state = scrollState,
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(
                                items = state.messages,
                                key = { it.id ?: it.getStableId() },
                                contentType = { it.role }
                            ) { msg ->
                                // REINFORCED_ISOLATION: Isolate every bubble in its own graphics layer 
                                // to prevent dispatchGetDisplayList recursion on heavy markdown content.
                                Box(modifier = Modifier.graphicsLayer { clip = true }) {
                                    KiriMessageBubble(msg)
                                }
                            }
                            
                            if (state.isSending) {
                                item(key = "typing_indicator", contentType = "system") { 
                                    Box(modifier = Modifier.graphicsLayer { clip = true }) {
                                        TypingIndicator()
                                    }
                                }
                            }
                        }
                    }

                    if (state.isLoadingMessages) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.primary)
                    }
                }

                // Chat Input Bar at the bottom of the Column
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, MaterialTheme.colorScheme.background.copy(alpha = 0.4f))
                            )
                        )
                ) {
                    ChatInputBar(
                        message = state.inputMessage,
                        onMessageChange = { viewModel.onMessageChange(it) },
                        onSend = { viewModel.sendMessage() },
                        onAttachClick = { filePickerLauncher.launch("*/*") },
                        selectedFileUri = state.selectedFileUri,
                        selectedFileName = state.selectedFileName,
                        onClearFile = { viewModel.clearSelectedFile() },
                        isSending = state.isSending
                    )
                }
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
