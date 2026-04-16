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
import com.kiri.ai.utils.findActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedPlan by remember { mutableStateOf("premium_monthly") }

    LaunchedEffect(uiState.orderData) {
        uiState.orderData?.let { data ->
            val checkout = Checkout()
            data.keyId?.let { checkout.setKeyID(it) }
            try {
                val options = JSONObject()
                options.put("name", "Kiri AI")
                options.put("description", "Premium Subscription")
                options.put("order_id", data.orderId)
                options.put("currency", data.currency ?: "INR")
                options.put("amount", data.amount)
                
                val prefill = JSONObject()
                prefill.put("email", "user@example.com") // Ideally from user data
                options.put("prefill", prefill)

                context.findActivity()?.let { activity ->
                    checkout.open(activity, options)
                } ?: run {
                    Toast.makeText(context, "Error: Could not find activity context", Toast.LENGTH_SHORT).show()
                }
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
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
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(32.dp))

            // Plan Toggle
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(4.dp)
            ) {
                val plans = listOf("premium_monthly" to "Monthly", "premium_yearly" to "Yearly")
                plans.forEach { (id, label) ->
                    val selected = selectedPlan == id
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(if (selected) MaterialTheme.colorScheme.primary else Color.Transparent)
                            .clickable { selectedPlan = id }
                            .padding(horizontal = 24.dp, vertical = 8.dp)
                    ) {
                        Text(
                            label,
                            color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
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
    val infiniteTransition = rememberInfiniteTransition(label = "pricing_card_glow")
    val borderAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "border_alpha"
    )

    val primaryColor = MaterialTheme.colorScheme.primary
    val outlineColor = MaterialTheme.colorScheme.outline
    val animatedBrush = remember(borderAlpha) {
        Brush.linearGradient(
            colors = listOf(
                primaryColor.copy(alpha = borderAlpha),
                outlineColor.copy(alpha = borderAlpha)
            )
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                animatedBrush,
                RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(32.dp)) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    "PREMIUM // ACCESS",
                    style = KiriTypography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "₹$price", 
                    style = KiriTypography.displayLarge.copy(fontSize = 56.sp),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = period, 
                    style = KiriTypography.titleLarge, 
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), 
                    modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
                )
            }
            
            Text(
                text = "TECHNICAL_SPEC: UNLIMITED_REASONING_CAPACITY",
                style = KiriTypography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            features.forEach { feature ->
                Row(modifier = Modifier.padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Check, 
                            contentDescription = null, 
                            tint = MaterialTheme.colorScheme.primary, 
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = feature.uppercase(), 
                        style = KiriTypography.labelMedium, 
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            
            KiriButton(
                text = "UPGRADE_SYSTEM",
                onClick = onUpgrade,
                modifier = Modifier.fillMaxWidth(),
                isLoading = isLoading,
                shape = RoundedCornerShape(2.dp) // Professional sharp button
            )
        }
    }
}

