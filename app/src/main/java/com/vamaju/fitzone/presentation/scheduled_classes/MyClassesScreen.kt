package com.vamaju.fitzone.presentation.scheduled_classes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.LineBreak
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

// Definir los colores para reutilizarlos.
private val OnBackgroundPrimary = Color(0xFF111418)
private val OnBackgroundSecondary = Color(0xFF60758a)
private val BorderLightGray = Color(0xFFf0f2f5)
private val PrimaryBlue = Color(0xFF3d98f4)
private val NavBarInactive = Color(0xFF60758a)
private val NavBarActive = Color(0xFF111418)

// Modelos de datos para la UI
data class ClassItem(
    val gymName: String,
    val className: String,
    val imageUrl: String
)

/**
 * Composable principal de la pantalla de "My Classes".
 *
 * Muestra una lista de clases próximas con una barra de navegación inferior.
 */
@Composable
fun MyClassesScreen() {
    val upcomingClasses = listOf(
        ClassItem(
            gymName = "FitZone - Downtown",
            className = "Yoga Flow",
            imageUrl = "https://picsum.photos/60/60?random=9"
        ),
        ClassItem(
            gymName = "FitZone - Midtown",
            className = "Spin Class",
            imageUrl = "https://picsum.photos/60/60?random=10"
        ),
        ClassItem(
            gymName = "FitZone - Uptown",
            className = "Pilates",
            imageUrl = "https://picsum.photos/60/60?random=11"
        )
    )

    Scaffold(
        topBar = {
            MyClassesTopBar()
        },
        bottomBar = {
            BottomNavigationBar()
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            item {
                SectionTitle(title = "Upcoming")
            }
            items(upcomingClasses) { classItem ->
                ClassListItem(classItem = classItem, onClick = { /* Handle click to class details */ })
            }
        }
    }
}

/**
 * Barra superior de la pantalla de "My Classes".
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyClassesTopBar() {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "My Classes",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnBackgroundPrimary
                    )
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { /* Open drawer/menu */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_list_regular),
                    contentDescription = "Menu",
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
 * Título de sección reutilizable (ajustado para el estilo de esta pantalla).
 */
@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.Bold,
            color = OnBackgroundPrimary,
            lineHeight = 28.sp,
            letterSpacing = (-0.015).sp
        ),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 12.dp)
    )
}

/**
 * Elemento de la lista de clases.
 */
@Composable
fun ClassListItem(classItem: ClassItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = classItem.imageUrl,
                contentDescription = "Class image",
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = classItem.gymName,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                    color = OnBackgroundPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = classItem.className,
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnBackgroundSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    //lineBreak = LineBreak.Simple
                )
            }
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_caret_right_regular),
            contentDescription = "View details",
            tint = OnBackgroundPrimary,
            modifier = Modifier.size(24.dp)
        )
    }
}

/**
 * Barra de navegación inferior.
 */
@Composable
fun BottomNavigationBar() {
    val navItems = listOf(
        "Home" to R.drawable.ic_house_regular,
        "Classes" to R.drawable.ic_magnifying_glass_regular,
        "My Classes" to R.drawable.ic_bookmark_fill,
        "Profile" to R.drawable.ic_user_regular
    )

    // Simulate active item
    val activeItem = "My Classes"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(bottom = 20.dp) // Add padding to match the HTML layout
    ) {
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(BorderLightGray))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom
        ) {
            navItems.forEach { (label, iconResId) ->
                val isActive = label == activeItem
                val iconColor = if (isActive) NavBarActive else NavBarInactive
                val textColor = if (isActive) NavBarActive else NavBarInactive

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { /* Handle navigation */ }
                ) {
                    Icon(
                        painter = painterResource(id = iconResId),
                        contentDescription = label,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                        color = textColor
                    )
                }
            }
        }
    }
}

/**
 * Vista previa de la pantalla de "My Classes".
 *
 * NOTA: Para que los iconos personalizados funcionen en la vista previa,
 * debes agregarlos como Drawables vectoriales en `app/src/main/res/drawable`.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyClassesScreenPreview() {
    FitZoneTheme {
        MyClassesScreen()
    }
}