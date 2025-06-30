package com.vamaju.fitzone.presentation.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vamaju.fitzone.presentation.home.model.ClassTypeData

/**
 * @author Juan Camilo Collantes Tovar on 29/06/2025
 * **/
@Composable
fun ClassTypeCardItem (
    item: ClassTypeData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp), // Define una altura fija para los items
        shape = RoundedCornerShape(16.dp), // Bordes redondeados
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // 1. Imagen de fondo desde URL
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true) // Animación suave al cargar la imagen
                    .build(),
                contentDescription = "Fondo para ${item.name}",
                contentScale = ContentScale.Crop, // Escala la imagen para llenar el contenedor
                modifier = Modifier.fillMaxSize()
            )

            // 2. Scrim (opcional pero recomendado)
            // Un gradiente oscuro en la parte inferior para que el texto sea legible
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 300f, // Empieza el gradiente más abajo
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            // 3. Contenido de texto (Título y Descripción)
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart) // Alinea el contenido abajo y a la izquierda
                    .padding(16.dp) // Padding interno
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// Preview para ver el diseño en Android Studio
@Preview(showBackground = true)
@Composable
fun ItemCardPreview() {
    val sampleItem = ClassTypeData(
        id = "1",
        name = "Explora la Naturaleza",
        description = "Una descripción corta de lo que esta tarjeta representa. Puede tener varias líneas.",
        imageUrl = "https://picsum.photos/seed/picsum/800/600" // URL de ejemplo
    )
    ClassTypeCardItem(item = sampleItem, onClick = {})
}