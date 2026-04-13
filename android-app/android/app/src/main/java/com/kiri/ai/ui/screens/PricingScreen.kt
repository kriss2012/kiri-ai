package com.kiri.ai.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kiri.ai.ui.components.KiriButton
import com.kiri.ai.ui.theme.*
import com.kiri.ai.ui.viewmodels.SubscriptionViewModel
import com.razorpay.Checkout
import org.json.JSONObject
import android.app.Activity
import android.widget.Toast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PricingScreen(
    navController: NavController,
    viewModel: SubscriptionViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState
    var selectedPlan by remember { mutableStateOf("premium_monthly") }

    LaunchedEffect(uiState.orderData) {
        uiState.orderData?.let { data ->
            val checkout = Checkout()
            checkout.setKeyID(data["keyId"] as String)
            try {
                val options = JSONObject()
                options.put("name", "Kiri AI")
                options.put("description", "Premium Subscription")
                options.put("order_id", data["orderId"])
                options.put("currency", data["currency"])
                options.put("amount", data["amount"])
                
                val prefill = JSONObject()
                prefill.put("email", "user@example.com") // Ideally from user data
                options.put("prefill", prefill)

                checkout.open(context as Activity, options)
            } catch (e: Exception) {
                Toast.makeText(context, "Error in payment: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect(uiState.success) {
        if (uiState.success) {
            Toast.makeText(context, "Subscription Successful!", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Choose Plan", style = KiriTypography.titleLarge) },
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
                text = "Unlock the full power of Kiri AI",
                style = KiriTypography.displayLarge.copy(fontSize = 28.sp),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(32.dp))

            // Plan Toggle
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(BorderCream)
                    .padding(4.dp)
            ) {
                val plans = listOf("premium_monthly" to "Monthly", "premium_yearly" to "Yearly")
                plans.forEach { (id, label) ->
                    val selected = selectedPlan == id
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(if (selected) TerracottaBrand else Color.Transparent)
                            .clickable { selectedPlan = id }
                            .padding(horizontal = 24.dp, vertical = 8.dp)
                    ) {
                        Text(
                            label,
                            color = if (selected) Ivory else OliveGray,
                            style = KiriTypography.labelLarge
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            PricingPlanCardEnhanced(
                name = if (selectedPlan == "premium_monthly") "Monthly" else "Yearly",
                price = if (selectedPlan == "premium_monthly") "149" else "1500",
                period = if (selectedPlan == "premium_monthly") "/mo" else "/yr",
                features = listOf(
                    "Unlimited daily requests",
                    "Priority response speed",
                    "Deep reasoning models",
                    "Advanced Image generation",
                    "Early access to new features"
                ),
                isLoading = uiState.isLoading,
                onUpgrade = { viewModel.createOrder(selectedPlan) }
            )

            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Secure payment powered by Razorpay",
                style = KiriTypography.labelMedium,
                color = StoneGray
            )

            if (uiState.error != null) {
                Text(
                    text = uiState.error!!,
                    color = Color.Red,
                    style = KiriTypography.bodySmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
fun PricingPlanCardEnhanced(
    name: String,
    price: String,
    period: String,
    features: List<String>,
    isLoading: Boolean,
    onUpgrade: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val borderAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                2.dp,
                Brush.linearGradient(
                    colors = listOf(
                        TerracottaBrand.copy(alpha = borderAlpha),
                        Color(0xFF6200EE).copy(alpha = borderAlpha)
                    )
                ),
                RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Ivory)
    ) {
        Column(modifier = Modifier.padding(32.dp)) {
            Box(
                modifier = Modifier
                    .background(TerracottaBrand.copy(alpha = 0.1f), RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    "PREMIUM",
                    style = KiriTypography.labelMedium.copy(
                        color = TerracottaBrand,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(verticalAlignment = Alignment.Bottom) {
                Text(text = "₹$price", style = KiriTypography.displayLarge.copy(fontSize = 56.sp))
                Text(
                    text = period, 
                    style = KiriTypography.titleLarge, 
                    color = StoneGray, 
                    modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
                )
            }
            
            Text(
                text = "Perfect for power users who want more.",
                style = KiriTypography.bodySmall,
                color = OliveGray
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            features.forEach { feature ->
                Row(modifier = Modifier.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(TerracottaBrand.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Check, 
                            contentDescription = null, 
                            tint = TerracottaBrand, 
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = feature, style = KiriTypography.bodyMedium, color = AnthropicNearBlack)
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            
            KiriButton(
                text = "Upgrade to $name",
                onClick = onUpgrade,
                modifier = Modifier.fillMaxWidth(),
                isLoading = isLoading,
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}
