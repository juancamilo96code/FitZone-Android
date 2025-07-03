package com.vamaju.fitzone.presentation.notification

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vamaju.fitzone.domain.notifications.model.Notification
import com.vamaju.fitzone.presentation.commons.composables.topbar.AppToolbar
import com.vamaju.fitzone.ui.theme.FitZoneTheme
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * @author Juan Camilo Collantes Tovar on 28/06/2025
 * **/

@Composable
fun NotificationsScreen(
    viewModel: NotificationViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Detalle del Artículo",
                titleAlignment = TextAlign.Start,
                startButtonIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onStartButtonClick = onBackClick
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
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
                        Text(
                            text = uiState.errorMessage!!, color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                uiState.notifications.isEmpty() -> {
                    item {
                        Text(
                            text = "No tienes notificaciones.",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                else -> {
                    items(uiState.notifications, key = { it.id }) { notification ->
                        NotificationCard(
                            modifier = Modifier.padding(16.dp),
                            notification = notification,
                            onNotificationClick = { viewModel.onNotificationClicked(notification.id) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificationsScreenPreview() {
    FitZoneTheme {
        NotificationsScreen(onBackClick = {})
    }
}

@Composable
fun NotificationCard(
    notification: Notification,
    onNotificationClick: (String) -> Unit,
    modifier: Modifier
) {
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onNotificationClick(notification.id) }, // Permite marcar como leída al hacer clic
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notification Icon",
                modifier = Modifier.size(24.dp),
                tint = if (notification.isRead) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = if (notification.isRead) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (notification.isRead) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = dateFormat.format(notification.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (notification.isRead) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onPrimaryContainer.copy(
                        alpha = 0.7f
                    )
                )
            }
        }
    }
}