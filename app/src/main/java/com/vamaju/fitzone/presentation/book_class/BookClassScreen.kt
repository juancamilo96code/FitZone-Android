package com.vamaju.fitzone.presentation.book_class

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vamaju.fitzone.presentation.book_class.composables.SubscriptionTypeCard
import com.vamaju.fitzone.presentation.class_details.composables.ScheduleOptionCard
import com.vamaju.fitzone.presentation.commons.composables.topbar.AppToolbar
import com.vamaju.fitzone.ui.theme.FitZoneTheme

/**
 * @author Juan Camilo Collantes Tovar on 28/06/2025
 * **/

@Composable
fun BookClassScreen(
    viewModel: BookingViewModel = hiltViewModel(),
    classId: String,
    onBackClick: () -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.loadClassDetailsAndCheckSubscription(classId = classId)
    }
    val uiState by viewModel.uiState.collectAsState()

    var selectedSubscriptionId by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Detalle del Artículo",
                titleAlignment = TextAlign.Start,
                startButtonIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onStartButtonClick = onBackClick
            )
        },
        bottomBar = {
            BookClassBottomBar(onBookClass = viewModel::onBookClassClicked)
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            uiState.classDetails?.let { classDetails ->
                item {
                    ScheduleOptionCard(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        classItem = classDetails,
                        isSelected = true,
                        onClick = {}
                    )
                }
            }


            if (uiState.isLoading) {
                item { CircularProgressIndicator() }

            } else if (uiState.errorMessage != null) {
                item {
                    Text(
                        text = uiState.errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }

            } else if (uiState.bookingSuccess) {
                item {
                    Text(
                        "¡Clase agendada con éxito!",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            } else {
                if (uiState.hasActiveSubscription) {
                    item {
                        Text(
                            "Tu suscripción actual:",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            uiState.activeSubscriptionDetails ?: "Suscripción activa",
                            modifier = Modifier.padding(top = 16.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    item {
                        SectionTitle(title = "Pay plans", modifier = Modifier.padding(top = 16.dp))
                    }

                    items(uiState.subscriptionOptions) { subscription ->
                        SubscriptionTypeCard(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            subscription = subscription,
                            isSelected = subscription.id == selectedSubscriptionId,
                            onClick = { clickedSubscription ->
                                selectedSubscriptionId = clickedSubscription
                            }
                        )
                    }
                }
            }

        }
    }
    if (uiState.showConfirmationModal) {
        AlertDialog(
            onDismissRequest = viewModel::onCancelBooking,
            title = { Text("Confirmar agendamiento") },
            text = { Text("¿Estás seguro de que quieres agendar esta clase? Si tienes una suscripción activa, se consumirá un cupo.") },
            confirmButton = {
                Button(onClick = viewModel::onConfirmBooking) {
                    Text("Sí, agendar")
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel::onCancelBooking) {
                    Text("Cancelar")
                }
            }
        )
    }
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
            lineHeight = 24.sp,
            letterSpacing = (-0.015).sp
        ),
        modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp)
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
                text = "Agendar Clase",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.015.sp
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookClassScreenPreview() {
    FitZoneTheme {
        BookClassScreen(
            classId = "1",
            onBackClick = {})
    }
}