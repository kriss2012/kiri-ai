package com.kiri.ai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kiri.ai.ui.components.KiriButton
import com.kiri.ai.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PricingScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pricing", style = KiriTypography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Parchment)
            )
        },
        containerColor = Parchment
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Simple, Honest Pricing",
                style = KiriTypography.displayLarge.copy(fontSize = 32.sp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Start free, upgrade when you need more. No hidden fees.",
                style = KiriTypography.bodyLarge,
                color = OliveGray,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            PricingPlanCard(
                name = "Free",
                price = "0",
                features = listOf("50 daily requests", "Standard response speed", "Standard model access"),
                isFeatured = false
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            PricingPlanCard(
                name = "Premium",
                price = "149",
                features = listOf("Unlimited daily requests", "Priority response speed", "Deep reasoning models", "Early access to new features"),
                isFeatured = true
            )
            
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
fun PricingPlanCard(
    name: String,
    price: String,
    features: List<String>,
    isFeatured: Boolean
) {
    androidx.compose.material3.Card(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = if (isFeatured) Ivory else Ivory.copy(alpha = 0.5f)
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isFeatured) 2.dp else 1.dp,
            color = if (isFeatured) TerracottaBrand else BorderCream
        )
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            if (isFeatured) {
                Badge(containerColor = TerracottaBrand, contentColor = Ivory) {
                    Text("Most Popular", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            Text(text = name.uppercase(), style = KiriTypography.labelLarge, color = OliveGray)
            
            Row(verticalAlignment = Alignment.Bottom) {
                Text(text = "₹$price", style = KiriTypography.displayLarge.copy(fontSize = 48.sp))
                Text(text = "/month", style = KiriTypography.bodyMedium, color = StoneGray, modifier = Modifier.padding(bottom = 8.dp))
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            features.forEach { feature ->
                Row(modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = TerracottaBrand, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = feature, style = KiriTypography.bodyMedium, color = OliveGray)
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            KiriButton(
                text = if (price == "0") "Current Plan" else "Upgrade Now",
                onClick = { /* Implement payment flow */ },
                enabled = price != "0",
                containerColor = if (isFeatured) TerracottaBrand else WarmSand,
                contentColor = if (isFeatured) Ivory else CharcoalWarm
            )
        }
    }
}
