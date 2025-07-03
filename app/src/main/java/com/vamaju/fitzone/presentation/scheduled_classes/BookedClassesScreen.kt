package com.vamaju.fitzone.presentation.scheduled_classes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vamaju.fitzone.presentation.class_details.composables.ScheduleOptionCard
import com.vamaju.fitzone.presentation.commons.composables.topbar.AppToolbar
import com.vamaju.fitzone.ui.theme.FitZoneTheme

/**
 * @author Juan Camilo Collantes Tovar on 28/06/2025
 * **/

/**
 * Composable principal de la pantalla de "My Classes".
 *
 * Muestra una lista de clases próximas con una barra de navegación inferior.
 */

@Composable
fun BookedClassesScreen(
    viewModel: BookedClassesViewModel = hiltViewModel(),
    navigateToBookClass: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedClassId by remember { mutableStateOf<String?>(null) }
    Scaffold(
        topBar = {
            AppToolbar(
                title = "Detalle del Artículo",
                titleAlignment = TextAlign.Start,
                startButtonIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onStartButtonClick = onBackClick
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
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
                        Text(text = uiState.errorMessage!!,
                            modifier = Modifier.padding(16.dp),)
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
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                        )
                    }


                    if (uiState.upcomingClasses.isEmpty()) {
                        item {
                            Text("No tienes próximas clases agendadas.",
                                modifier = Modifier.padding(16.dp))
                        }
                    } else {
                        items(uiState.upcomingClasses, key = { it.id }) { classItem ->
                            ScheduleOptionCard(
                                modifier = Modifier.padding(horizontal = 16.dp),
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
                                .padding(vertical = 8.dp,horizontal = 16.dp)
                        )
                    }


                    if (uiState.pastClasses.isEmpty()) {
                        item {
                            Text("No tienes clases pasadas agendadas.",
                                modifier = Modifier.padding(16.dp))
                        }
                    } else {
                        items(uiState.pastClasses, key = { it.id }) { classItem ->
                            ScheduleOptionCard(
                                modifier = Modifier.padding(horizontal = 16.dp),
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
                )
            }
        },
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
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Button(
            onClick = onBookClass,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
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
        BookedClassesScreen(navigateToBookClass = {}, onBackClick = {})
    }
}