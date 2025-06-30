package com.vamaju.fitzone.presentation.commons.composables.search_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/

private val OnBackgroundPrimary = Color(0xFF111418)
private val OnBackgroundSecondary = Color(0xFF60758a)
private val BackgroundSurface = Color(0xFFf0f2f5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier, placeholder: String) {

    TextField(
        value = "",
        onValueChange = {},
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp)),
        placeholder = {
            Text(text = placeholder, color = OnBackgroundSecondary)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon",
                tint = OnBackgroundSecondary,
                modifier = Modifier.size(24.dp)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = BackgroundSurface,
            unfocusedContainerColor = BackgroundSurface,
            disabledContainerColor = BackgroundSurface,
            cursorColor = OnBackgroundPrimary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = OnBackgroundPrimary)
    )
}