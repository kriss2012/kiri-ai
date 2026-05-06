package com.kiriai.kiriorganization.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiriai.kiriorganization.ui.theme.*

/**
 * Bugatti Monogram Input Field
 * 
 * Aesthetic:
 * - Technical Monochrome (#000000 / #FFFFFF)
 * - 6px subtle corner radius
 * - Monospace CAPS label
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KiriTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isError: Boolean = false,
    errorMessage: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column(modifier = modifier) {
        // Technical Mono Label
        Text(
            text = label.uppercase(),
            style = KiriTypography.labelMedium.copy(color = SilverMist)
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeholder.uppercase(),
                    style = KiriTypography.labelMedium.copy(color = SilverMist.copy(alpha = 0.5f))
                )
            },
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f),
                unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.1f),
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                errorBorderColor = MaterialTheme.colorScheme.error
            ),
            singleLine = true,
            textStyle = KiriTypography.bodyMedium
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage.uppercase(),
                color = SilverMist,
                style = KiriTypography.labelMedium,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            )
        }
    }
}
