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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kiri.ai.R
import com.kiri.ai.ui.components.*
import com.kiri.ai.ui.theme.*
import com.kiri.ai.ui.viewmodels.ChatViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val state = viewModel.uiState
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    val context = LocalContext.current

    // File Picker Launcher
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                val name = context.contentResolver.query(it, null, null, null, null)?.use { cursor ->
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    cursor.moveToFirst()
                    cursor.getString(nameIndex)
                }
                viewModel.onFileSelected(it, name)
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
                    items(state.conversations) { conv ->
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
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = ShowroomWhite)
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("pricing") }) {
                            Icon(Icons.Default.Star, contentDescription = "Premium", tint = ShowroomWhite)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = VelvetBlack,
                        titleContentColor = ShowroomWhite
                    )
                )
            },
            bottomBar = {
                ChatInputBar(
                    message = state.inputMessage,
                    onMessageChange = { viewModel.onMessageChange(it) },
                    onSend = { viewModel.sendMessage() },
                    onAttachClick = { filePickerLauncher.launch(arrayOf("*/*")) },
                    selectedFileName = state.selectedFileName,
                    onClearFile = { viewModel.clearSelectedFile() },
                    isSending = state.isSending
                )
            },
            containerColor = VelvetBlack
        ) { padding ->
            Box(modifier = Modifier.padding(padding).fillMaxSize()) {
                LazyColumn(
                    state = scrollState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(state.messages) { msg ->
                        KiriMessageBubble(msg)
                    }
                    
                    if (state.isSending) {
                        item { TypingIndicator() }
                    }
                }

                if (state.messages.isEmpty() && !state.isLoadingMessages) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            "READY_FOR_INPUT",
                            style = KiriTypography.labelLarge.copy(color = SilverMist.copy(alpha = 0.2f))
                        )
                    }
                }
                
                if (state.isLoadingMessages) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = ShowroomWhite)
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
        modifier = Modifier.padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "KIRI_IS_THINKING",
            style = KiriTypography.labelMedium.copy(color = ShowroomWhite.copy(alpha = alpha))
        )
    }
}
