package com.kiriai.kiriorganization.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kiriai.kiriorganization.ui.components.KiriButton
import com.kiriai.kiriorganization.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageLabScreen(navController: NavController) {
    var prompt by remember { mutableStateOf("") }
    var isGenerating by remember { mutableStateOf(false) }
    var imageUrl by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val isDark = isSystemInDarkTheme()
    val bgColor = if (isDark) BrutalistBlack else BrutalistWhite
    val textColor = if (isDark) BrutalistWhite else BrutalistBlack
    val cardBg = if (isDark) BrutalistDarkGray else BrutalistWhite
    val shadowColor = if (isDark) BrutalistLightGray else BrutalistBlack

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { 
                        Text(
                            "IMAGE_LAB // V2.0", 
                            style = KiriTypography.headlineMedium,
                            fontWeight = FontWeight.Black,
                            color = textColor
                        ) 
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = textColor)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = bgColor,
                        titleContentColor = textColor
                    )
                )
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display Area (Neo-Brutalist Box)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                // Offset shadow layer
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .offset(x = 6.dp, y = 6.dp)
                        .background(color = shadowColor, shape = RoundedCornerShape(12.dp))
                        .border(width = 3.dp, color = textColor, shape = RoundedCornerShape(12.dp))
                )

                // Foreground container
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(cardBg, RoundedCornerShape(12.dp))
                        .border(width = 3.dp, color = textColor, shape = RoundedCornerShape(12.dp))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (isGenerating) {
                        CircularProgressIndicator(color = textColor, strokeWidth = 3.dp)
                    } else if (imageUrl != null) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "Generated Image",
                            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Fit
                        )
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.Image, 
                                contentDescription = null, 
                                tint = textColor, 
                                modifier = Modifier.size(56.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "WAITING_FOR_PROMPT...", 
                                style = KiriTypography.labelMedium.copy(fontWeight = FontWeight.Black), 
                                color = if (isDark) BrutalistLightGray else BrutalistDarkGray
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Input Area (Neo-Brutalist text field design)
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "DESCRIBE ARTIFACT PROMPT",
                    style = KiriTypography.labelMedium.copy(
                        color = textColor,
                        fontWeight = FontWeight.Black
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = prompt,
                    onValueChange = { prompt = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 3.dp,
                            color = textColor,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    placeholder = { 
                        Text(
                            "E.G. CYBERPUNK CITY STREETS, 8K...", 
                            style = KiriTypography.labelMedium.copy(
                                color = if (isDark) BrutalistLightGray.copy(alpha = 0.6f) else BrutalistDarkGray.copy(alpha = 0.6f)
                            )
                        ) 
                    },
                    textStyle = KiriTypography.bodyMedium.copy(
                        color = textColor,
                        fontWeight = FontWeight.Bold
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = BrutalistYellow,
                        unfocusedContainerColor = cardBg,
                        cursorColor = textColor,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor
                    ),
                    shape = RoundedCornerShape(8.dp),
                    maxLines = 3
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            KiriButton(
                text = if (isGenerating) "GENERATING..." else "GENERATE_ARTIFACT",
                onClick = { 
                    isGenerating = true
                    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                        isGenerating = false
                        Toast.makeText(context, "API_CONNECTION_PENDING", Toast.LENGTH_LONG).show()
                    }, 2000)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = prompt.isNotBlank() && !isGenerating
            )
        }
    }
}
