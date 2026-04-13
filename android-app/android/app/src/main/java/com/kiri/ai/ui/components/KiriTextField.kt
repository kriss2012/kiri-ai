package com.kiri.ai.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
            text = label.uppercase(),
            style = KiriTypography.labelLarge.copy(
                fontSize = 12.sp,
                color = OliveGray,
                letterSpacing = 0.5.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
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
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = FocusBlue,
                unfocusedBorderColor = BorderCream,
                containerColor = Ivory,
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
                style = KiriTypography.labelLarge,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
