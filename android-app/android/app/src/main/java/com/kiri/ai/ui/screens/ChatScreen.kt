package com.kiri.ai.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kiri.ai.data.models.Conversation
import com.kiri.ai.ui.components.FadeUpAnimation
import com.kiri.ai.ui.components.KiriMessageBubble
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

    LaunchedEffect(state.messages.size) {
        if (state.messages.isNotEmpty()) {
            scrollState.animateScrollToItem(state.messages.size - 1)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = DarkSurface,
                drawerContentColor = Ivory
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Kiri AI",
                    style = KiriTypography.headlineLarge,
                    modifier = Modifier.padding(horizontal = 24.dp),
                    color = Ivory
                )
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = { 
                        viewModel.newChat()
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaBrand)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("New Chat")
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    "Recent",
                    style = KiriTypography.labelLarge,
                    color = StoneGray,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
                
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(state.conversations) { conv ->
                        NavigationDrawerItem(
                            label = { Text(conv.title, color = Ivory, maxLines = 1) },
                            selected = conv.id == state.currentConversationId,
                            onClick = {
                                viewModel.selectConversation(conv.id)
                                scope.launch { drawerState.close() }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.Transparent,
                                selectedContainerColor = TerracottaBrand.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                }
                
                HorizontalDivider(color = OliveGray)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("profile") }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(40.dp).background(TerracottaBrand, androidx.compose.foundation.shape.CircleShape), contentAlignment = Alignment.Center) {
                        Text(state.user?.name?.take(1) ?: "U", color = Ivory)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(state.user?.name ?: "User", color = Ivory, style = KiriTypography.bodyMedium)
                        Text(state.user?.plan ?: "Free Plan", color = StoneGray, fontSize = 12.sp)
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(state.currentTitle, style = KiriTypography.titleLarge) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("pricing") }) {
                            Icon(Icons.Default.Star, contentDescription = "Upgrade", color = TerracottaBrand)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Parchment)
                )
            },
            bottomBar = {
                ChatInputBar(
                    message = state.inputMessage,
                    onMessageChange = { viewModel.onMessageChange(it) },
                    onSend = { viewModel.sendMessage() },
                    isSending = state.isSending
                )
            },
            containerColor = Parchment
        ) { padding ->
            Box(modifier = Modifier.padding(padding).fillMaxSize()) {
                if (state.messages.isEmpty() && !state.isLoadingMessages) {
                    WelcomeScreen()
                } else {
                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(state.messages) { msg ->
                            // Soft entrance for messages
                            var visible by remember { mutableStateOf(false) }
                            LaunchedEffect(Unit) { visible = true }
                            AnimatedVisibility(
                                visible = visible,
                                enter = fadeIn(animationSpec = androidx.compose.animation.core.tween(500)) +
                                        slideInVertically(initialOffsetY = { 20 })
                            ) {
                                KiriMessageBubble(msg)
                            }
                        }
                    }
                }
                
                if (state.isLoadingMessages) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = TerracottaBrand)
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen() {
    var animate by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { animate = true }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FadeUpAnimation(visible = animate) {
            Text("Kiri AI", style = KiriTypography.displayLarge, color = TerracottaBrand)
        }
        Spacer(modifier = Modifier.height(16.dp))
        FadeUpAnimation(visible = animate, delayMillis = 200) {
            Text("What can I help you with today?", style = KiriTypography.bodyLarge, color = OliveGray)
        }
        Spacer(modifier = Modifier.height(32.dp))
        // Welcome Illustration (organic, hand-drawn)
        FadeUpAnimation(visible = animate, delayMillis = 400) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Ivory, RoundedCornerShape(32.dp))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                // Mock for drawable/welcome_illustration.png
                Text("Organic Illustration", color = StoneGray, textAlign = TextAlign.Center)
            }
        }
    }
}
