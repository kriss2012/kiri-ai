package com.kiri.ai.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.graphics.Brush
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
import com.kiri.ai.data.models.Conversation
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

    // Error handling
    val context = LocalContext.current
    LaunchedEffect(state.error) {
        state.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    val totalItems = (state.messages?.size ?: 0) + (if (state.isSending) 1 else 0)
    LaunchedEffect(totalItems) {
        if (totalItems > 0) {
            try {
                val lastIndex = totalItems - 1
                scrollState.animateScrollToItem(lastIndex)
            } catch (e: Exception) {
                // Ignore scroll errors to prevent app crash
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = DarkSurface,
                drawerContentColor = Ivory,
                drawerShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Kiri AI",
                    style = KiriTypography.headlineMedium,
                    modifier = Modifier.padding(horizontal = 24.dp),
                    color = Ivory
                )
                Spacer(modifier = Modifier.height(24.dp))
                
                KiriButton(
                    text = "New Chat",
                    onClick = { 
                        viewModel.newChat()
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    containerColor = Color.Transparent,
                    contentColor = Ivory,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Brush.linearGradient(LogoGradient))
                )

                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    "RECENT CONVERSATIONS",
                    style = KiriTypography.labelMedium.copy(
                        color = StoneGray,
                        letterSpacing = 0.5.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
                
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(
                        items = state.conversations,
                        key = { it.getStableId() }
                    ) { conv ->
                        NavigationDrawerItem(
                            label = { 
                                Text(
                                    conv.title ?: "Untitled Chat", 
                                    color = if (conv.id == state.currentConversationId) Ivory else WarmSilver, 
                                    maxLines = 1,
                                    style = KiriTypography.bodySmall
                                ) 
                            },
                            selected = conv.id == state.currentConversationId,
                            onClick = {
                                conv.id?.let { viewModel.selectConversation(it) }
                                scope.launch { drawerState.close() }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.Transparent,
                                selectedContainerColor = DarkSurface.copy(alpha = 0.5f),
                                selectedIconColor = Ivory,
                                unselectedIconColor = WarmSilver
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                }
                
                HorizontalDivider(color = OliveGray.copy(alpha = 0.3f), modifier = Modifier.padding(vertical = 8.dp))
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("profile") }
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(WarmSand, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            state.user?.name?.take(1) ?: "U", 
                            color = CharcoalWarm,
                            style = KiriTypography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            state.user?.name ?: "User", 
                            color = Ivory, 
                            style = KiriTypography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Text(
                            state.user?.plan ?: "Free Plan", 
                            color = StoneGray, 
                            style = KiriTypography.labelMedium
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                            title = { 
                        Text(
                            state.currentTitle ?: "Kiri AI", 
                            style = KiriTypography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        ) 
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = AnthropicNearBlack)
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("pricing") }) {
                            Icon(Icons.Default.Star, contentDescription = "Upgrade", tint = TerracottaBrand)
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
                if ((state.messages?.isEmpty() ?: true) && !state.isLoadingMessages) {
                    WelcomeScreen()
                } else {
                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(
                            items = state.messages ?: emptyList(),
                            key = { it.getStableId() }
                        ) { msg ->
                            KiriMessageBubble(msg)
                        }
                        
                        if (state.isSending) {
                            item(key = "typing_indicator") {
                                TypingIndicator()
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
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "Kiri AI Logo",
                modifier = Modifier.size(100.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        FadeUpAnimation(visible = animate, delayMillis = 200) {
            Text(
                "Kiri AI", 
                style = KiriTypography.displayLarge.copy(
                    fontSize = 40.sp,
                    brush = Brush.linearGradient(LogoGradient)
                ), 
                color = TerracottaBrand
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        FadeUpAnimation(visible = animate, delayMillis = 400) {
            Text(
                "Your intelligent companion for seamless conversations and creative insights.", 
                style = KiriTypography.bodyLarge, 
                color = OliveGray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TypingIndicator() {
    val infiniteTransition = rememberInfiniteTransition(label = "typing")
    
    val dot1 by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "dot1"
    )
    val dot2 by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis = 200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "dot2"
    )
    val dot3 by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis = 400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "dot3"
    )

    Row(
        modifier = Modifier
            .padding(vertical = 12.dp)
            .background(Ivory, RoundedCornerShape(12.dp))
            .border(1.dp, BorderCream, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AIAvatar()
        Spacer(modifier = Modifier.width(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            listOf(dot1, dot2, dot3).forEach { alpha ->
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(TerracottaBrand.copy(alpha = alpha), CircleShape)
                )
            }
        }
    }
}
