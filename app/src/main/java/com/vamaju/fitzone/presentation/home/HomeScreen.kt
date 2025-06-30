package com.vamaju.fitzone.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vamaju.fitzone.presentation.home.composables.ClassTypeCardItem
import com.vamaju.fitzone.presentation.home.model.ClassTypeData
import com.vamaju.fitzone.ui.theme.FitZoneTheme

/**
 * @author Juan Camilo Collantes Tovar on 28/06/2025
 * **/

// Definir los colores y la tipografía para reutilizarlos (igual que en el ejemplo anterior).
private val OnBackgroundPrimary = Color(0xFF111418)
private val OnBackgroundSecondary = Color(0xFF60758a)
private val BackgroundSurface = Color(0xFFf0f2f5)

val list = listOf(
    ClassTypeData(
        id = "1",
        name = "Explora la Naturaleza",
        description = "Una descripción corta de lo que esta tarjeta representa. Puede tener varias líneas.",
        imageUrl = "https://picsum.photos/seed/picsum/800/600" // URL de ejemplo
    ),
    ClassTypeData(
        id = "1",
        name = "Explora la Naturaleza",
        description = "Una descripción corta de lo que esta tarjeta representa. Puede tener varias líneas.",
        imageUrl = "https://picsum.photos/seed/picsum/800/600" // URL de ejemplo
    ),
    ClassTypeData(
        id = "1",
        name = "Explora la Naturaleza",
        description = "Una descripción corta de lo que esta tarjeta representa. Puede tener varias líneas.",
        imageUrl = "https://picsum.photos/seed/picsum/800/600" // URL de ejemplo
    ),
    ClassTypeData(
        id = "1",
        name = "Explora la Naturaleza",
        description = "Una descripción corta de lo que esta tarjeta representa. Puede tener varias líneas.",
        imageUrl = "https://picsum.photos/seed/picsum/800/600" // URL de ejemplo
    ),
    ClassTypeData(
        id = "1",
        name = "Explora la Naturaleza",
        description = "Una descripción corta de lo que esta tarjeta representa. Puede tener varias líneas.",
        imageUrl = "https://picsum.photos/seed/picsum/800/600" // URL de ejemplo
    ),
    ClassTypeData(
        id = "1",
        name = "Explora la Naturaleza",
        description = "Una descripción corta de lo que esta tarjeta representa. Puede tener varias líneas.",
        imageUrl = "https://picsum.photos/seed/picsum/800/600" // URL de ejemplo
    )
)


@Composable
fun HomeScreen(
    //viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit
) {
    Scaffold(
        topBar = {
            HomeTopBar(onNotificationsClick = { /* Handle click */ })
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {

            SearchBar(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                placeholder = "Find a class"
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(list) { item ->
                    ClassTypeCardItem(
                        item = item,
                        onClick = {
                            navigateToDetail(item.id)
                        }
                    )
                }
            }
        }
    }
}

/**
 * Barra superior de la pantalla de inicio.
 */
@Composable
fun HomeTopBar(onNotificationsClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Título "FitZone"
        // 1. Usar un Spacer con weight para alinear el título al centro.
        Spacer(Modifier.weight(1f))
        Text(
            text = "FitZone",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = OnBackgroundPrimary
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(3f) // Ocupa más espacio para centrar
        )

        // Botón de notificaciones
        // 2. Usar IconButton para el botón de icono.
        Box(
            modifier = Modifier.size(48.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(
                onClick = onNotificationsClick,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = OnBackgroundPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

/**
 * Barra de búsqueda.
 *
 * @OptIn(ExperimentalMaterial3Api::class) se usa para habilitar las APIs experimentales.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier, placeholder: String) {
    // 3. Usar TextField de Material 3.
    TextField(
        value = "", // En una implementación real, esto sería un estado mutable.
        onValueChange = { /* Update search query state */ },
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    FitZoneTheme {
        HomeScreen {}
    }
}