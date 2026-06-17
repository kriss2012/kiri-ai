package com.kiriai.kiriorganization.ui.components

import androidx.compose.foundation.border
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
import com.kiriai.kiriorganization.ui.theme.*

/**
 * Neo-Brutalist Input Field with thick borders and a yellow accent focus state.
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
        Text(
            text = label.uppercase(),
            style = KiriTypography.labelMedium.copy(
                color = BrutalistBlack,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 3.dp,
                    color = if (isError) KiriError else BrutalistBlack,
                    shape = RoundedCornerShape(8.dp)
                ),
            placeholder = {
                Text(
                    text = placeholder.uppercase(),
                    style = KiriTypography.labelMedium.copy(color = BrutalistDarkGray.copy(alpha = 0.6f))
                )
            },
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                focusedContainerColor = BrutalistYellow,
                unfocusedContainerColor = BrutalistWhite,
                cursorColor = BrutalistBlack,
                focusedTextColor = BrutalistBlack,
                unfocusedTextColor = BrutalistBlack,
                errorTextColor = BrutalistBlack
            ),
            singleLine = true,
            textStyle = KiriTypography.bodyMedium.copy(
                color = BrutalistBlack,
                fontWeight = FontWeight.Bold
            )
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage.uppercase(),
                color = KiriError,
                style = KiriTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            )
        }
    }
}
