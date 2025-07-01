package com.vamaju.fitzone.presentation.book_class

import android.R
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vamaju.fitzone.domain.classes.model.ClassModel
import com.vamaju.fitzone.presentation.book_class.composables.SubscriptionTypeCard
import com.vamaju.fitzone.presentation.book_class.model.SubscriptionTypes
import com.vamaju.fitzone.presentation.class_details.composables.ScheduleOptionCard
import com.vamaju.fitzone.presentation.class_details.model.ClassDetailUiState
import com.vamaju.fitzone.ui.theme.FitZoneTheme

/**
 * @author Juan Camilo Collantes Tovar on 28/06/2025
 * **/

private val OnBackgroundPrimary = Color(0xFF111418)
private val OnBackgroundSecondary = Color(0xFF60758a)
private val PrimaryBlue = Color(0xFF3d98f4)
private val BorderGray = Color(0xFFdbe0e6)


@Composable
fun BookClassScreen(
    viewModel: BookingViewModel = hiltViewModel(),
    classId: String,
    onClose: () -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.loadClassDetailsAndCheckSubscription(classId = classId)
    }
    val uiState by viewModel.uiState.collectAsState()

    var selectedSubscriptionId by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            BookClassTopBar(onClose = onClose)
        },
        bottomBar = {
            BookClassBottomBar(onBookClass  = viewModel::onBookClassClicked)
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {

            uiState.classDetails?.let { classDetails ->
                item {
                    ScheduleOptionCard(
                        classItem = classDetails,
                        isSelected = true,
                        onClick = {}
                    )
                }
            }


            if (uiState.isLoading) {
                item { CircularProgressIndicator() }

            } else if (uiState.errorMessage != null) {
                item { Text(text = uiState.errorMessage!!, color = MaterialTheme.colorScheme.error)}

            } else if (uiState.bookingSuccess) {
                item { Text("¡Clase agendada con éxito!", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary) }
            } else {
                if (uiState.hasActiveSubscription) {
                    item {
                        Text("Tu suscripción actual:", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(uiState.activeSubscriptionDetails ?: "Suscripción activa", style = MaterialTheme.typography.bodyLarge)
                    }
                } else {
                    item {
                        SectionTitle(title = "Pay plans")
                    }

                    items(uiState.subscriptionOptions) { subscription ->
                        SubscriptionTypeCard(
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
 * Barra superior de la pantalla de reserva.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookClassTopBar(onClose: () -> Unit) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Book ClassModel",
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
            onClose = {})
    }
}