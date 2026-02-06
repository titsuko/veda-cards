package com.example.vedaapplication.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vedaapplication.ui.theme.ButtonColor
import com.example.vedaapplication.ui.theme.VedaApplicationTheme

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val textColor = MaterialTheme.colorScheme.onSurface
    val borderColor = MaterialTheme.colorScheme.outline
    val labelColor = MaterialTheme.colorScheme.onSurfaceVariant
    val accentColor = ButtonColor

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,

        visualTransformation = if (isPassword && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },

        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPassword) KeyboardType.Password else keyboardType,
            imeAction = imeAction
        ),

        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,

            focusedBorderColor = borderColor,
            unfocusedBorderColor = borderColor,

            cursorColor = accentColor,
            selectionColors = androidx.compose.foundation.text.selection.TextSelectionColors(
                handleColor = accentColor,
                backgroundColor = accentColor.copy(alpha = 0.4f)
            ),

            focusedLabelColor = labelColor,
            unfocusedLabelColor = labelColor,
        ),

        trailingIcon = {
            if (value.isNotEmpty()) {
                val iconImage: ImageVector
                val onClickAction: () -> Unit

                if (isPassword) {
                    iconImage = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    onClickAction = { passwordVisible = !passwordVisible }
                } else {
                    iconImage = Icons.Filled.Clear
                    onClickAction = { onValueChange("") }
                }

                IconButton(onClick = onClickAction) {
                    Icon(
                        imageVector = iconImage,
                        contentDescription = null,
                        tint = accentColor
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun AppTextFieldPreview() {
    VedaApplicationTheme(darkTheme = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            var text by remember { mutableStateOf("test@veda.com") }
            AppTextField(
                value = text,
                onValueChange = { text = it },
                label = "Email"
            )

            Spacer(Modifier.height(16.dp))

            var password by remember { mutableStateOf("123456") }
            AppTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                isPassword = true
            )
        }
    }
}