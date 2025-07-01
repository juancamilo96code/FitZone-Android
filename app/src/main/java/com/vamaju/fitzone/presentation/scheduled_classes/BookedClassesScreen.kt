package com.vamaju.fitzone.presentation.scheduled_classes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vamaju.fitzone.presentation.class_details.composables.ScheduleOptionCard
import com.vamaju.fitzone.ui.theme.FitZoneTheme

/**
 * @author Juan Camilo Collantes Tovar on 28/06/2025
 * **/

/**
 * Composable principal de la pantalla de "My Classes".
 *
 * Muestra una lista de clases próximas con una barra de navegación inferior.
 */

private val OnBackgroundPrimary = Color(0xFF111418)
private val OnBackgroundSecondary = Color(0xFF60758a)
private val BackgroundSurface = Color(0xFFf0f2f5)
private val PrimaryBlue = Color(0xFF3d98f4)
private val GrayBackground = Color(0xFFdbe0e6)

/**
 * Composable principal de la pantalla de detalles de la clase.
 *
 * Usa un `Scaffold` con una barra superior, un contenido desplazable y una barra inferior.
 */
@Composable
fun BookedClassesScreen(
    viewModel: BookedClassesViewModel = hiltViewModel(),
    navigateToBookClass: (String) -> Unit,
    onClose: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
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

            when {
                uiState.isLoading -> {
                    item {
                        CircularProgressIndicator()
                    }
                }

                uiState.errorMessage != null -> {
                    item {
                        Text(text = uiState.errorMessage!!, color = MaterialTheme.colorScheme.error)
                    }
                }

                else -> {
                    item {
                        // Sección de Próximas Clases
                        Text(
                            text = "Próximas Clases",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                    }


                    if (uiState.upcomingClasses.isEmpty()) {
                        item {
                            Text("No tienes próximas clases agendadas.")
                        }
                    } else {
                        items(uiState.upcomingClasses, key = { it.id }) { classItem ->
                            ScheduleOptionCard(
                                classItem = classItem,
                                isSelected = true,
                                onClick = {}) // Reutiliza tu componente de tarjeta de clase
                        }
                    }

                    item {
                        // Sección de Clases Pasadas
                        Text(
                            text = "Clases Pasadas",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                    }


                    if (uiState.pastClasses.isEmpty()) {
                        item {
                            Text("No tienes clases pasadas agendadas.")
                        }
                    } else {
                        items(uiState.pastClasses, key = { it.id }) { classItem ->
                            ScheduleOptionCard(
                                classItem = classItem,
                                isSelected = false,
                                onClick = {}) // Reutiliza tu componente de tarjeta de clase
                        }
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

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "ClassModel Details",
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
                text = "Book ClassModel",
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
fun MyClassesScreenPreview() {
    FitZoneTheme {
        BookedClassesScreen(navigateToBookClass = {}, onClose = {})
    }
}