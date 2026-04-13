package com.kiri.ai.ui.components

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
import com.kiri.ai.ui.theme.*

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
        Text(
            text = label,
            style = KiriTypography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = OliveGray
            )
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeholder,
                    style = KiriTypography.bodyMedium.copy(color = StoneGray)
                )
            },
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            shape = RoundedCornerShape(8.dp), // Comfortably rounded
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FocusBlue,
                unfocusedBorderColor = BorderCream,
                focusedContainerColor = Ivory,
                unfocusedContainerColor = Ivory,
                cursorColor = AnthropicNearBlack,
                errorBorderColor = ErrorCrimson
            ),
            singleLine = true,
            textStyle = KiriTypography.bodyMedium
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = ErrorCrimson,
                style = KiriTypography.labelMedium,
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
            )
        }
    }
}
