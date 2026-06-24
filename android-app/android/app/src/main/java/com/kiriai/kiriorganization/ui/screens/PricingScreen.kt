package com.kiriai.kiriorganization.ui.screens

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.kiriai.kiriorganization.ui.components.KiriButton
import com.kiriai.kiriorganization.ui.theme.*
import com.kiriai.kiriorganization.ui.viewmodels.SubscriptionViewModel
import com.kiriai.kiriorganization.utils.findActivity
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
                title = { Text("INTEL // SUBSCRIPTION", style = KiriTypography.labelLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = ShowroomWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = ShowroomWhite,
                    navigationIconContentColor = ShowroomWhite
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(VelvetBlack, DeepSpaceBlue)))
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "HYPER-SCALE REASONING",
                style = KiriTypography.headlineMedium.copy(letterSpacing = 4.sp),
                color = ShowroomWhite,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Select an operational tier to increase throughput.",
                style = KiriTypography.labelSmall.copy(color = SilverMist),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(40.dp))

            // Plan Toggle
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .border(0.5.dp, ShowroomWhite.copy(alpha = 0.1f), RoundedCornerShape(24.dp)),
                color = GlassWhite
            ) {
                Row(modifier = Modifier.padding(4.dp)) {
                    val plans = listOf("premium_monthly" to "MONTHLY", "premium_yearly" to "ANNUAL")
                    plans.forEach { (id, label) ->
                        val selected = selectedPlan == id
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (selected) ShowroomWhite else Color.Transparent)
                                .clickable { selectedPlan = id }
                                .padding(horizontal = 24.dp, vertical = 10.dp)
                        ) {
                            Text(
                                label,
                                color = if (selected) VelvetBlack else SilverMist,
                                style = KiriTypography.labelMedium.copy(fontWeight = FontWeight.Bold)
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
    val primaryColor = MaterialTheme.colorScheme.primary
    val outlineColor = MaterialTheme.colorScheme.outline
    
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(32.dp)) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f), RoundedCornerShape(50))
                    .border(0.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), RoundedCornerShape(50))
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
                modifier = Modifier.fillMaxWidth().height(56.dp),
                isLoading = isLoading,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}

