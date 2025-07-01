package com.vamaju.fitzone.presentation.class_details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.vamaju.fitzone.presentation.class_details.composables.ScheduleOptionCard
import com.vamaju.fitzone.presentation.commons.composables.SectionTitle
import com.vamaju.fitzone.presentation.commons.composables.bottom_bar.BookClassBottomBar
import com.vamaju.fitzone.presentation.commons.composables.topbar.ClassDetailsTopBar
import com.vamaju.fitzone.ui.theme.FitZoneTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @author Juan Camilo Collantes Tovar on 28/06/2025
 * **/

private val OnBackgroundPrimary = Color(0xFF111418)
private val OnBackgroundSecondary = Color(0xFF60758a)
private val BackgroundSurface = Color(0xFFf0f2f5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassTypeDetailsScreen(
    viewModel: ClassTypeDetailsViewModel = hiltViewModel(),
    classTypeId: String,
    navigateToBookClass: (String) -> Unit,
    onClose: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var showDatePicker by remember { mutableStateOf(true) }
    val datePickerState = rememberDatePickerState()

    var showCityDropdown by remember { mutableStateOf(true) }

    var selectedClassId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadInitialData(classTypeId)
        viewModel.observeFilteredClasses(classTypeId)
    }

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
            uiState.classType?.let { classType ->
                // Imagen del ClassType
                item {
                    AsyncImage(
                        model = classType.imageUrl,
                        contentDescription = "ClassModel image",
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
                            text = classType.name,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnBackgroundPrimary,
                                lineHeight = 28.sp,
                                letterSpacing = (-0.015).sp
                            ),
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(
                            text = classType.description,
                            style = MaterialTheme.typography.bodyLarge.copy(color = OnBackgroundPrimary),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }
            }

            item {

                // Inputs de filtro (Fecha y Ciudad)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Input de Fecha
                    val dateFormatter = remember {
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    }
                    OutlinedTextField(
                        value = uiState.selectedDate?.let {
                            dateFormatter.format(it)
                        } ?: "Seleccionar fecha",
                        onValueChange = { /* No se usa aquí */ },
                        readOnly = true, // Para que no se pueda escribir manualmente
                        modifier = Modifier
                            .weight(1f)
                            .clickable { showDatePicker = true },
                        label = { Text("Fecha") }
                    )

                    // Input de Ciudad (con Dropdown)
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = uiState.availableLocations.find { it.id == uiState.selectedLocationId }?.name
                                ?: "Seleccionar ciudad",
                            onValueChange = { /* No se usa aquí */ },
                            readOnly = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showCityDropdown = true },
                            label = { Text("Ciudad") }
                        )
                        DropdownMenu(
                            expanded = showCityDropdown,
                            onDismissRequest = { showCityDropdown = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            uiState.availableLocations.forEach { location ->
                                DropdownMenuItem(
                                    text = { Text(location.name) },
                                    onClick = {
                                        viewModel.onCitySelected(location.id)
                                        showCityDropdown = false
                                    }
                                )
                            }
                        }
                    }
                }

            }

            item {
                TextButton(
                    onClick = viewModel::clearFilters,
                    modifier = Modifier
                        //.align(Alignment.End)
                        .padding(end = 16.dp)
                ) {
                    Text("Limpiar filtros")
                }
            }

            item {
                Text(
                    text = "Clases disponibles",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnBackgroundPrimary
                    ),
                    modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
                )

            }

            when {
                uiState.isLoading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                uiState.errorMessage != null -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = uiState.errorMessage!!,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                }

                uiState.filteredClasses.isEmpty() -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "No se encontraron clases con los filtros seleccionados.")
                        }
                    }

                }

                else -> {
                    items(
                        uiState.filteredClasses,
                        key = { it.id }) { classItem ->
                        // ClassCard que muestra los detalles de la clase
                        ScheduleOptionCard(
                            classItem = classItem,
                            isSelected = classItem.id == selectedClassId,
                            onClick = {id->
                                selectedClassId = id
                            })
                    }
                }
            }

            if (selectedClassId != null) {
                item {
                    SectionTitle(title = "Location")
                    AsyncImage(
                        model = "https://www.bigbraincreation.com/bigbrain-images/bigbrain-banner/bigbrain-google-map.png",
                        contentDescription = "Location image",
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

    // Date Picker Dialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            viewModel.onDateSelected(Date(millis))
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClassDetailsScreenPreview() {
    FitZoneTheme {
        ClassTypeDetailsScreen(classTypeId = "Yoga", navigateToBookClass = {}, onClose = {})
    }
}