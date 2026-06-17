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
import androidx.compose.foundation.isSystemInDarkTheme

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
    val isDark = isSystemInDarkTheme()

    val bgColor = if (isDark) BrutalistBlack else BrutalistWhite
    val textColor = if (isDark) BrutalistWhite else BrutalistBlack
    val cardBg = if (isDark) BrutalistDarkGray else BrutalistWhite
    val shadowColor = if (isDark) BrutalistLightGray else BrutalistBlack

    // Error Handling
    LaunchedEffect(state.error) {
        state.error?.let {
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

    // LOAD_INITIAL_CONVERSATION
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

    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = Color.Black.copy(alpha = 0.6f),
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = bgColor,
                drawerContentColor = textColor,
                drawerShape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
                    .border(
                        width = 3.dp, 
                        color = textColor,
                        shape = RoundedCornerShape(0.dp)
                    )
            ) {
                Spacer(modifier = Modifier.height(64.dp))
                Text(
                    text = "KIRI // ATELIER",
                    style = KiriTypography.headlineMedium.copy(fontWeight = FontWeight.Black),
                    modifier = Modifier.padding(horizontal = 24.dp),
                    color = textColor
                )
                Spacer(modifier = Modifier.height(24.dp))
                
                KiriButton(
                    text = "NEW_SESSION",
                    onClick = { 
                        viewModel.newChat()
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    "OPERATION_MODES",
                    style = KiriTypography.labelMedium.copy(color = if (isDark) BrutalistLightGray else BrutalistDarkGray),
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )

                NavigationDrawerItem(
                    label = { Text("CORE_CHAT", style = KiriTypography.labelMedium.copy(fontWeight = FontWeight.Black)) },
                    selected = true,
                    onClick = { scope.launch { drawerState.close() } },
                    icon = { Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(18.dp), tint = BrutalistBlack) },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.Transparent,
                        selectedContainerColor = BrutalistYellow,
                        selectedTextColor = BrutalistBlack,
                        unselectedTextColor = textColor
                    ),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .border(2.dp, textColor, RoundedCornerShape(6.dp))
                )

                NavigationDrawerItem(
                    label = { Text("IMAGE_LAB", style = KiriTypography.labelMedium.copy(fontWeight = FontWeight.Black)) },
                    selected = false,
                    onClick = { 
                        navController.navigate("imagelab")
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(18.dp), tint = textColor) },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.Transparent,
                        selectedTextColor = textColor,
                        unselectedTextColor = textColor
                    ),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    "RECENT_LOGS",
                    style = KiriTypography.labelMedium.copy(color = if (isDark) BrutalistLightGray else BrutalistDarkGray),
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
                
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(
                        items = state.conversations,
                        key = { it.id ?: "conv_${it.hashCode()}" }
                    ) { conv ->
                        val isSelected = conv.id == state.currentConversationId
                        NavigationDrawerItem(
                            label = { 
                                Text(
                                    conv.title?.uppercase() ?: "UNTITLED_LOG", 
                                    style = KiriTypography.labelMedium.copy(fontWeight = FontWeight.Black),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                ) 
                            },
                            selected = isSelected,
                            onClick = {
                                conv.id?.let { viewModel.selectConversation(it) }
                                scope.launch { drawerState.close() }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.Transparent,
                                selectedContainerColor = BrutalistYellow,
                                selectedTextColor = BrutalistBlack,
                                unselectedTextColor = textColor
                            ),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                                .border(
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = if (isSelected) textColor else Color.Transparent,
                                    shape = RoundedCornerShape(6.dp)
                                )
                        )
                    }
                }
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .background(textColor)
                )

                // Profile Row (Technical)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("profile") }
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        state.user?.name?.uppercase() ?: "USER_NULL", 
                        style = KiriTypography.labelLarge.copy(fontWeight = FontWeight.Black),
                        color = textColor
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        Icons.Default.Settings, 
                        contentDescription = null, 
                        tint = textColor, 
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                Column {
                    CenterAlignedTopAppBar(
                        title = { 
                            Text(
                                state.currentTitle.uppercase(), 
                                style = KiriTypography.labelLarge.copy(
                                    letterSpacing = 2.sp,
                                    fontWeight = FontWeight.Black
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            ) 
                        },
                        modifier = Modifier.statusBarsPadding(),
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = textColor)
                            }
                        },
                        actions = {
                            val mainViewModel: MainViewModel = hiltViewModel()
                            val isDarkMode by mainViewModel.isDarkMode.collectAsStateWithLifecycle()
                            IconButton(onClick = { mainViewModel.toggleTheme() }) {
                                Icon(
                                    if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode, 
                                    contentDescription = "Toggle Theme",
                                    tint = textColor
                                )
                            }
                            IconButton(onClick = { navController.navigate("pricing") }) {
                                Icon(Icons.Default.Star, contentDescription = "Premium", tint = textColor)
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = bgColor,
                            titleContentColor = textColor,
                            navigationIconContentColor = textColor,
                            actionIconContentColor = textColor
                        )
                    )
                    // Bottom border divider on TopAppBar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .background(textColor)
                    )
                }
            },
            containerColor = bgColor
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(bgColor)
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
                                style = KiriTypography.labelSmall.copy(
                                    color = if (isDark) BrutalistLightGray else BrutalistDarkGray,
                                    fontWeight = FontWeight.Black
                                ),
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            Text(
                                text = "KIRI AI",
                                style = KiriTypography.displayLarge.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 4.sp
                                ),
                                textAlign = TextAlign.Center,
                                color = textColor
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = "Multimodal intelligence layer active. Send a message or upload an artifact to begin analysis.",
                                style = KiriTypography.bodyMedium.copy(
                                    color = if (isDark) BrutalistLightGray else BrutalistDarkGray,
                                    fontWeight = FontWeight.Bold
                                ),
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
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center), 
                            color = textColor,
                            strokeWidth = 3.dp
                        )
                    }
                }

                // Chat Input Bar at the bottom of the Column (with flat top divider border)
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .background(textColor)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(bgColor)
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
}

@Composable
fun TypingIndicator() {
    val infiniteTransition = rememberInfiniteTransition(label = "typing")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "alpha"
    )

    Row(
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .graphicsLayer { this.alpha = alpha },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "KIRI_IS_THINKING...",
            style = KiriTypography.labelMedium.copy(
                color = BrutalistBlack,
                fontWeight = FontWeight.Black
            )
        )
    }
}
