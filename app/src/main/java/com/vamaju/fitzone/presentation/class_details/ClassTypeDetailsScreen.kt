package com.vamaju.fitzone.presentation.class_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vamaju.fitzone.R
import com.vamaju.fitzone.ui.theme.FitZoneTheme

/**
 * @author Juan Camilo Collantes Tovar on 28/06/2025
 * **/

// Definir los colores y la tipografía para reutilizarlos.
private val OnBackgroundPrimary = Color(0xFF111418)
private val OnBackgroundSecondary = Color(0xFF60758a)
private val BackgroundSurface = Color(0xFFf0f2f5)
private val PrimaryBlue = Color(0xFF3d98f4)
private val GrayBackground = Color(0xFFdbe0e6)

// Modelo de datos para la reseña
data class Review(
    val author: String,
    val timeAgo: String,
    val rating: Int,
    val comment: String,
    val likes: Int,
    val dislikes: Int,
    val profilePictureUrl: String
)

/**
 * Composable principal de la pantalla de detalles de la clase.
 *
 * Usa un `Scaffold` con una barra superior, un contenido desplazable y una barra inferior.
 */
@Composable
fun ClassTypeDetailsScreen(onClose: () -> Unit) {
    Scaffold(
        topBar = {
            ClassDetailsTopBar(onClose = onClose)
        },
        bottomBar = {
            BookClassBottomBar(onBookClass = { /* Handle booking */ })
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Imagen principal de la clase
            AsyncImage(
                model = "https://picsum.photos/seed/picsum/800/600",
                contentDescription = "Class image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(0.dp)) // Sin redondeo en móvil, se aplica en tablet.
                    .padding(0.dp), // Sin padding en móvil
                contentScale = ContentScale.Crop
            )

            // Contenido de la clase
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                // Título y descripción
                Text(
                    text = "Yoga Flow",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnBackgroundPrimary,
                        lineHeight = 28.sp,
                        letterSpacing = (-0.015).sp
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = "A dynamic yoga class that links breath and movement to build strength, flexibility, and mindfulness. Suitable for all levels.",
                    style = MaterialTheme.typography.bodyLarge.copy(color = OnBackgroundPrimary),
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                // Dificultad
                SectionTitle(title = "Difficulty")
                DetailsItem(
                    iconRes = R.drawable.ic_chart_line_regular, // Reemplaza con tu icono de gráfico
                    title = "Intermediate",
                    subtitle = null
                )

                // Horario
                SectionTitle(title = "Filter")
                DetailsItem(
                    iconRes = R.drawable.ic_calendar_regular, // Reemplaza con tu icono de calendario
                    title = "Monday, July 15",
                    subtitle = "10:00 AM - 11:00 AM"
                )
                DetailsItem(
                    iconRes = R.drawable.ic_calendar_regular, // Reemplaza con tu icono de calendario
                    title = "Monday, July 15",
                    subtitle = "10:00 AM - 11:00 AM"
                )

                // Ubicación
                SectionTitle(title = "Location")
                AsyncImage(
                    model = "https://picsum.photos/600/300?random=2", // Imagen de mapa
                    contentDescription = "Location map",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .padding(vertical = 12.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                // Instructor
                SectionTitle(title = "Instructor")
                InstructorItem(
                    name = "Elena Ramirez",
                    title = "Certified Yoga Instructor",
                    imageUrl = "https://picsum.photos/200/200?random=3"
                )



                // Reseñas
                SectionTitle(title = "Reviews")
                ReviewsSummary(
                    rating = 4.8f,
                    totalReviews = 125,
                    ratings = mapOf(
                        5 to 0.70f,
                        4 to 0.20f,
                        3 to 0.05f,
                        2 to 0.03f,
                        1 to 0.02f
                    )
                )

                // Lista de comentarios
                val reviews = listOf(
                    Review(
                        "Sophia Carter",
                        "2 weeks ago",
                        5,
                        "This class was amazing! Elena's guidance was clear and the flow was challenging yet accessible. I left feeling energized and relaxed.",
                        15,
                        2,
                        "https://picsum.photos/200/200?random=4"
                    ),
                    Review(
                        "Liam Bennett",
                        "1 month ago",
                        4,
                        "Great class overall. The instructor was knowledgeable and the pace was good. The studio could be a bit cleaner.",
                        8,
                        1,
                        "https://picsum.photos/200/200?random=5"
                    )
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    reviews.forEach { review ->
                        ReviewCard(review = review)
                    }
                }
            }
        }
    }
}

/**
 * Barra superior de la pantalla de detalles.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDetailsTopBar(onClose: () -> Unit) {
    TopAppBar(
        title = {
            // El título está centrado usando un Box y Modifiers.
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Class Details",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnBackgroundPrimary
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(end = 48.dp) // Offset para centrar con el icono.
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = OnBackgroundPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}

/**
 * Título de sección reutilizable.
 */
@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = OnBackgroundPrimary,
            lineHeight = 24.sp,
            letterSpacing = (-0.015).sp
        ),
        modifier = modifier.padding(top = 24.dp, bottom = 8.dp)
    )
}

/**
 * Elemento de detalle con icono, título y subtítulo.
 */
@Composable
fun DetailsItem(
    iconRes: Int,
    title: String,
    subtitle: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(BackgroundSurface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = OnBackgroundPrimary,
                modifier = Modifier.size(24.dp)
            )
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = OnBackgroundPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = OnBackgroundSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * Elemento de instructor con foto de perfil.
 */
@Composable
fun InstructorItem(name: String, title: String, imageUrl: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Instructor profile picture",
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = OnBackgroundPrimary
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = OnBackgroundSecondary
            )
        }
    }
}

/**
 * Resumen de las reseñas con calificación y barras de progreso.
 */
@Composable
fun ReviewsSummary(rating: Float, totalReviews: Int, ratings: Map<Int, Float>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Columna de la calificación y estrellas
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$rating",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Black,
                    fontSize = 40.sp,
                    letterSpacing = (-0.033).sp
                ),
                color = OnBackgroundPrimary
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                repeat(5) { index ->
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = if (index < rating) OnBackgroundPrimary else GrayBackground,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Text(
                text = "$totalReviews reviews",
                style = MaterialTheme.typography.bodyLarge,
                color = OnBackgroundPrimary
            )
        }

        // Gráfico de barras de calificaciones
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ratings.toSortedMap(compareByDescending { it }).forEach { (stars, percentage) ->
                RatingBar(stars = stars, percentage = percentage)
            }
        }
    }
}

/**
 * Barra de progreso para las calificaciones.
 */
@Composable
fun RatingBar(stars: Int, percentage: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "$stars",
            style = MaterialTheme.typography.bodyMedium,
            color = OnBackgroundPrimary
        )
        LinearProgressIndicator(
            progress = { percentage },
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = OnBackgroundPrimary,
            trackColor = GrayBackground
        )
        Text(
            text = "${(percentage * 100).toInt()}%",
            style = MaterialTheme.typography.bodySmall,
            color = OnBackgroundSecondary,
            textAlign = TextAlign.End,
            modifier = Modifier.width(40.dp)
        )
    }
}

/**
 * Tarjeta de reseña individual.
 */
@Composable
fun ReviewCard(review: Review) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Encabezado de la reseña
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = review.profilePictureUrl,
                contentDescription = "Reviewer profile picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = review.author,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                    color = OnBackgroundPrimary
                )
                Text(
                    text = review.timeAgo,
                    style = MaterialTheme.typography.bodySmall,
                    color = OnBackgroundSecondary
                )
            }
        }

        // Estrellas de calificación
        RatingStars(rating = review.rating)

        // Comentario
        Text(
            text = review.comment,
            style = MaterialTheme.typography.bodyLarge,
            color = OnBackgroundPrimary
        )

        // Botones de "Me gusta" y "No me gusta"
        ReviewActions(likes = review.likes, dislikes = review.dislikes)
    }
}

/**
 * Fila de estrellas para mostrar una calificación.
 */
@Composable
fun RatingStars(rating: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        repeat(5) { index ->
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (index < rating) OnBackgroundPrimary else GrayBackground,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

/**
 * Fila de botones de acción para las reseñas.
 */
@Composable
fun ReviewActions(likes: Int, dislikes: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(36.dp),
        modifier = Modifier.padding(top = 8.dp)
    ) {
        // Usar iconos de Material Icons o tus propios drawables.
        // Aquí se usaría el icono de ThumbsUp desde los recursos si lo tienes.
        // painterResource(id = R.drawable.ic_thumbs_up_regular)
        // O un Icono predefinido de Material Icons si es similar.
        ActionButton(iconRes = R.drawable.ic_thumbs_up_regular, text = "$likes")
        ActionButton(iconRes = R.drawable.ic_thumbs_down_regular, text = "$dislikes")
    }
}

/**
 * Botón de acción para reseña con icono y texto.
 */
@Composable
fun ActionButton(iconRes: Int, text: String) {
    Button(
        onClick = { /* Handle action */ },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = OnBackgroundSecondary
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Text(text = text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

/**
 * Barra inferior con el botón de reserva.
 */
@Composable
fun BookClassBottomBar(onBookClass: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Button(
            onClick = onBookClass,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            Text(
                text = "Book Class",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

/**
 * Vista previa de la pantalla de detalles de la clase.
 *
 * NOTA: Para que los iconos personalizados (`ic_calendar_regular`, etc.) funcionen en la
 * vista previa, debes agregarlos como recursos en `app/src/main/res/drawable`.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClassDetailsScreenPreview() {
    FitZoneTheme {
        ClassTypeDetailsScreen(onClose = {})
    }
}