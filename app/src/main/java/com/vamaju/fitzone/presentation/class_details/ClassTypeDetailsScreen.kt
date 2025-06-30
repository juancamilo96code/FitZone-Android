package com.vamaju.fitzone.presentation.class_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.vamaju.fitzone.presentation.class_details.composables.ScheduleOptionCard
import com.vamaju.fitzone.presentation.class_details.model.ClassDetails
import com.vamaju.fitzone.ui.theme.FitZoneTheme

/**
 * @author Juan Camilo Collantes Tovar on 28/06/2025
 * **/

private val OnBackgroundPrimary = Color(0xFF111418)
private val OnBackgroundSecondary = Color(0xFF60758a)
private val BackgroundSurface = Color(0xFFf0f2f5)
private val PrimaryBlue = Color(0xFF3d98f4)
private val GrayBackground = Color(0xFFdbe0e6)

data class Review(
    val author: String,
    val timeAgo: String,
    val rating: Int,
    val comment: String,
    val likes: Int,
    val dislikes: Int,
    val profilePictureUrl: String
)

val list = listOf(
    ClassDetails(
        id = "1",
        typeName = "Yoga Flow Avanzado",
        date = "2025-07-15",
        time = "18:30",
        duration = "01:15",
        address = "Carrera 10 # 20-30, Bogotá D.C.",
        locationId = "LOC001",
        locationName = "FitZone Centro",
        latitude = 4.6097,
        longitude = -74.0817,
        instructorName = "Ana Smith",
    ), ClassDetails(
        id = "2",
        typeName = "Yoga Flow Avanzado",
        date = "2025-07-15",
        time = "18:30",
        duration = "01:15",
        address = "Carrera 10 # 20-30, Bogotá D.C.",
        locationId = "LOC001",
        locationName = "FitZone Centro",
        latitude = 4.6097,
        longitude = -74.0817,
        instructorName = "Ana Smith",
    ), ClassDetails(
        id = "3",
        typeName = "Yoga Flow Avanzado",
        date = "2025-07-15",
        time = "18:30",
        duration = "01:15",
        address = "Carrera 10 # 20-30, Bogotá D.C.",
        locationId = "LOC001",
        locationName = "FitZone Centro",
        latitude = 4.6097,
        longitude = -74.0817,
        instructorName = "Ana Smith",
    )
)

/**
 * Composable principal de la pantalla de detalles de la clase.
 *
 * Usa un `Scaffold` con una barra superior, un contenido desplazable y una barra inferior.
 */
@Composable
fun ClassTypeDetailsScreen(
    navigateToBookClass: (String) -> Unit,
    onClose: () -> Unit
) {
    var selectedClassId by remember { mutableStateOf<String?>(null) }
    Scaffold(
        topBar = {
            ClassDetailsTopBar(onClose = onClose)
        },
        bottomBar = {
            BookClassBottomBar(onBookClass = { selectedClassId?.let { navigateToBookClass(it) } })
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            item {
                AsyncImage(
                    model = "https://picsum.photos/seed/picsum/800/600",
                    contentDescription = "Class image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape(0.dp))
                        .padding(0.dp),
                    contentScale = ContentScale.Crop
                )

            }

            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
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

                    SectionTitle(title = "Difficulty")
                    DetailsItem(
                        iconRes = R.drawable.ic_chart_line_regular,
                        title = "Intermediate",
                        subtitle = null
                    )
                }
            }

            item {

                SectionTitle(title = "Filter")
                DetailsItem(
                    iconRes = R.drawable.ic_calendar_regular,
                    title = "Monday, July 15",
                    subtitle = "10:00 AM - 11:00 AM"
                )
                DetailsItem(
                    iconRes = R.drawable.ic_calendar_regular,
                    title = "Monday, July 15",
                    subtitle = "10:00 AM - 11:00 AM"
                )

            }

            item {
                Text(
                    text = "Próximas Clases",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnBackgroundPrimary
                    ),
                    modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
                )
            }

            items(list) { classDetail ->

                ScheduleOptionCard(
                    classDetails = classDetail,
                    isSelected = classDetail.id == selectedClassId,
                    onClick = { clickedClass ->
                        selectedClassId =
                            clickedClass.id
                    }
                )
            }

            item {
                SectionTitle(title = "Location")
                AsyncImage(
                    model = "https://picsum.photos/seed/picsum/800/600",
                    contentDescription = "Class image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape(0.dp))
                        .padding(0.dp),
                    contentScale = ContentScale.Crop
                )
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
                    modifier = Modifier.padding(end = 48.dp)
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
        ClassTypeDetailsScreen(navigateToBookClass = {}, onClose = {})
    }
}