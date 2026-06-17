package com.kiriai.kiriorganization.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kiriai.kiriorganization.ui.components.KiriButton
import com.kiriai.kiriorganization.ui.theme.*
import com.kiriai.kiriorganization.ui.viewmodels.SubscriptionViewModel
import com.kiriai.kiriorganization.utils.findActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.razorpay.Checkout
import org.json.JSONObject
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
    val isDark = isSystemInDarkTheme()
    val bgColor = if (isDark) BrutalistBlack else BrutalistWhite
    val textColor = if (isDark) BrutalistWhite else BrutalistBlack
    val toggleBg = if (isDark) BrutalistDarkGray else BrutalistWhite

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
                prefill.put("email", "user@example.com")
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
            Column {
                TopAppBar(
                    title = { 
                        Text(
                            "INTEL // SUBSCRIPTION", 
                            style = KiriTypography.headlineMedium, 
                            fontWeight = FontWeight.Black,
                            color = textColor
                        ) 
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.Default.ArrowBack, 
                                contentDescription = "Back", 
                                tint = textColor
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = bgColor,
                        titleContentColor = textColor,
                        navigationIconContentColor = textColor
                    )
                )
                // Thick black divider line below TopAppBar
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
                .padding(padding)
                .fillMaxSize()
                .background(bgColor)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "HYPER-SCALE REASONING",
                style = KiriTypography.headlineLarge,
                color = textColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black
            )
            
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Select an operational tier to increase throughput.",
                style = KiriTypography.labelMedium.copy(
                    color = if (isDark) BrutalistLightGray else BrutalistDarkGray,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(32.dp))

            // Plan Toggle - Brutalist styling
            Box(
                modifier = Modifier
                    .background(toggleBg, RoundedCornerShape(8.dp))
                    .border(3.dp, textColor, RoundedCornerShape(8.dp))
                    .padding(4.dp)
            ) {
                Row {
                    val plans = listOf("premium_monthly" to "MONTHLY", "premium_yearly" to "ANNUAL")
                    plans.forEach { (id, label) ->
                        val selected = selectedPlan == id
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (selected) BrutalistYellow else Color.Transparent)
                                .border(
                                    width = if (selected) 2.dp else 0.dp,
                                    color = if (selected) textColor else Color.Transparent,
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .clickable { selectedPlan = id }
                                .padding(horizontal = 24.dp, vertical = 10.dp)
                        ) {
                            Text(
                                label,
                                color = textColor,
                                style = KiriTypography.labelMedium.copy(fontWeight = FontWeight.Black)
                            )
                        }
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
    val isDark = isSystemInDarkTheme()
    val textColor = if (isDark) BrutalistWhite else BrutalistBlack
    val cardBg = if (isDark) BrutalistDarkGray else BrutalistWhite
    val shadowColor = if (isDark) BrutalistLightGray else BrutalistBlack

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Offset shadow layer
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 6.dp, y = 6.dp)
                .background(color = shadowColor, shape = RoundedCornerShape(12.dp))
                .border(width = 3.dp, color = textColor, shape = RoundedCornerShape(12.dp))
        )

        // Card Foreground Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(cardBg, RoundedCornerShape(12.dp))
                .border(width = 3.dp, color = textColor, shape = RoundedCornerShape(12.dp))
                .padding(32.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(BrutalistYellow, RoundedCornerShape(4.dp))
                    .border(2.dp, textColor, RoundedCornerShape(4.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    "PREMIUM // ACCESS",
                    style = KiriTypography.labelMedium.copy(
                        color = BrutalistBlack,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "₹$price", 
                    style = KiriTypography.displayLarge.copy(fontWeight = FontWeight.Black),
                    color = textColor
                )
                Text(
                    text = period, 
                    style = KiriTypography.headlineMedium, 
                    color = textColor.copy(alpha = 0.8f), 
                    modifier = Modifier.padding(bottom = 6.dp, start = 4.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(6.dp))
            
            Text(
                text = "TECHNICAL_SPEC: UNLIMITED_REASONING_CAPACITY",
                style = KiriTypography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = if (isDark) BrutalistLightGray else BrutalistDarkGray
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            features.forEach { feature ->
                Row(
                    modifier = Modifier.padding(vertical = 10.dp), 
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .background(BrutalistYellow, CircleShape)
                            .border(2.dp, textColor, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Check, 
                            contentDescription = null, 
                            tint = BrutalistBlack, 
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = feature.uppercase(), 
                        style = KiriTypography.labelMedium, 
                        color = textColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            KiriButton(
                text = "UPGRADE_SYSTEM",
                onClick = onUpgrade,
                modifier = Modifier.fillMaxWidth(),
                isLoading = isLoading
            )
        }
    }
}
