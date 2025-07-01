package com.vamaju.fitzone.presentation.notification

import androidx.compose.foundation.background
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
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.vamaju.fitzone.ui.theme.FitZoneTheme

/**
 * @author Juan Camilo Collantes Tovar on 28/06/2025
 * **/


val OnBackgroundPrimary = Color(0xFF111418)
val OnBackgroundSecondary = Color(0xFF60758a)
val BackgroundSurface = Color(0xFFf0f2f5)


data class Notification(
    val title: String,
    val time: String
)

data class NotificationGroup(
    val date: String,
    val notifications: List<Notification>
)

@Composable
fun NotificationsScreen(
    onBackClick: () -> Unit
) {

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

            val notificationsByDate = listOf(
                NotificationGroup(
                    date = "Today",
                    notifications = listOf(
                        Notification("Yoga ClassModel Reminder", "10:00 AM"),
                        Notification("New ClassModel Alert: HIIT", "12:30 PM")
                    )
                ),
                NotificationGroup(
                    date = "Yesterday",
                    notifications = listOf(
                        Notification("FitZone Promotion", "09:15 AM"),
                        Notification("ClassModel Schedule Update", "06:00 PM")
                    )
                )
            )

            notificationsByDate.forEach { group ->
                item {
                    NotificationHeader(date = group.date)
                }
                items(group.notifications) { notification ->
                    NotificationItem(notification = notification)
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
 * Encabezado de la sección de notificaciones.
 */
@Composable
fun NotificationHeader(date: String) {
    Text(
        text = date,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = OnBackgroundPrimary,
            lineHeight = 24.sp,
            letterSpacing = (-0.015).sp
        ),
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
    )
}

/**
 * Elemento de la lista de notificaciones.
 */
@Composable
fun NotificationItem(notification: Notification) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp, 8.dp)
            .height(72.dp),
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
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notification icon",
                tint = OnBackgroundPrimary,
                modifier = Modifier.size(24.dp)
            )
        }


        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = notification.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    color = OnBackgroundPrimary
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = notification.time,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = OnBackgroundSecondary
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
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