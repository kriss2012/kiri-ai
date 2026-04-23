package com.kiriai.kiriorganization.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("IMAGE_LAB // V1.0", style = KiriTypography.labelLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = VelvetBlack, titleContentColor = ShowroomWhite)
            )
        },
        containerColor = VelvetBlack
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display Area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(DarkGray)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isGenerating) {
                    CircularProgressIndicator(color = NeonCyan)
                } else if (imageUrl != null) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Generated Image",
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Image, contentDescription = null, tint = SilverMist, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("WAITING_FOR_PROMPT...", style = KiriTypography.labelSmall, color = SilverMist)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Input Area
            OutlinedTextField(
                value = prompt,
                onValueChange = { prompt = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Describe the image...", style = KiriTypography.bodyMedium, color = SilverMist) },
                textStyle = KiriTypography.bodyMedium.copy(color = ShowroomWhite),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = NeonCyan,
                    unfocusedBorderColor = SilverMist.copy(alpha = 0.3f),
                    cursorColor = NeonCyan
                ),
                shape = RoundedCornerShape(8.dp),
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            KiriButton(
                text = if (isGenerating) "GENERATING..." else "GENERATE",
                onClick = { 
                    isGenerating = true
                    // Simulate generation for now or connect to API if ViewModel is ready
                    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                        isGenerating = false
                        Toast.makeText(context, "API_CONNECTION_PENDING", Toast.LENGTH_LONG).show()
                    }, 2000)
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = prompt.isNotBlank() && !isGenerating
            )
        }
    }
}
