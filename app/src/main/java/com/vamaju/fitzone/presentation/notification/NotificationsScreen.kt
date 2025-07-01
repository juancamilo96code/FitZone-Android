package com.vamaju.fitzone.presentation.notification

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vamaju.fitzone.domain.notifications.model.Notification
import com.vamaju.fitzone.ui.theme.FitZoneTheme
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * @author Juan Camilo Collantes Tovar on 28/06/2025
 * **/


val OnBackgroundPrimary = Color(0xFF111418)
val OnBackgroundSecondary = Color(0xFF60758a)
val BackgroundSurface = Color(0xFFf0f2f5)

@Composable
fun NotificationsScreen(
    viewModel: NotificationViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            NotificationsTopBar(onBackClick = onBackClick)
        },
        bottomBar = {
            BottomNavigationBar()
        },
        containerColor = Color.White
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
                        Text(text = uiState.errorMessage!!, color = MaterialTheme.colorScheme.error)
                    }
                }

                uiState.notifications.isEmpty() -> {
                    item {
                        Text(
                            text = "No tienes notificaciones.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                else -> {
                    items(uiState.notifications, key = { it.id }) { notification ->
                        NotificationCard(
                            notification = notification,
                            onNotificationClick = { viewModel.onNotificationClicked(notification.id) }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Encabezado de la barra superior.
 */
@Composable
fun NotificationsTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Go back",
                tint = OnBackgroundPrimary
            )
        }

        Spacer(Modifier.weight(1f))
        Text(
            text = "Notifications",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = OnBackgroundPrimary,
                lineHeight = 24.sp,
                letterSpacing = (-0.015).sp
            )
        )

        Spacer(Modifier.width(48.dp))
    }
}

/**
 * Barra de navegación inferior.
 */
@Composable
fun BottomNavigationBar() {
    Column(
        modifier = Modifier.background(Color.White)
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = Color(0xFFf0f2f5)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            BottomNavigationItem(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = true
            )
            BottomNavigationItem(
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                label = "Classes",
                isSelected = false
            )
            BottomNavigationItem(
                icon = Icons.Default.Person,
                label = "Profile",
                isSelected = false
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

/**
 * Elemento de la barra de navegación inferior.
 */
@Composable
fun BottomNavigationItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean
) {
    val contentColor = if (isSelected) OnBackgroundPrimary else OnBackgroundSecondary

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.width(64.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = contentColor,
                lineBreak = LineBreak.Simple
            )
        )
    }
}

/**
 * Vista previa para el composable principal.
 */
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
    onNotificationClick: (String) -> Unit
) {
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNotificationClick(notification.id) }, // Permite marcar como leída al hacer clic
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
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