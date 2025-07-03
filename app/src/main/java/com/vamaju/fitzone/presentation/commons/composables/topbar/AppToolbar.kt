package com.vamaju.fitzone.presentation.commons.composables.topbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * @author Juan Camilo Collantes Tovar on 03/07/2025
 * **/

/**
 * Un componente de barra de herramientas personalizable para tu aplicación.
 *
 * @param title El título a mostrar en la barra de herramientas.
 * @param modifier Modificador para aplicar a este componente.
 * @param titleAlignment La alineación horizontal del título (izquierda o centro).
 * @param startButtonIcon El icono para el botón de inicio (izquierda). Si es nulo, no se muestra el botón.
 * @param onStartButtonClick La acción a realizar cuando se hace clic en el botón de inicio.
 * @param endButtonIcon El icono para el botón final (derecha). Si es nulo, no se muestra el botón.
 * @param onEndButtonClick La acción a realizar cuando se hace clic en el botón final.
 * @param showNotificationBadge Si se debe mostrar una insignia de notificación en el botón final.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    title: String,
    modifier: Modifier = Modifier,
    titleAlignment: TextAlign = TextAlign.Start,
    startButtonIcon: ImageVector? = null,
    onStartButtonClick: (() -> Unit)? = null,
    endButtonIcon: ImageVector? = null,
    onEndButtonClick: (() -> Unit)? = null,
    showNotificationBadge: Boolean = false
) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = when (titleAlignment) {
                    TextAlign.Center -> Alignment.Center
                    else -> Alignment.CenterStart
                }
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = titleAlignment,
                    modifier = Modifier.then(
                        if (titleAlignment == TextAlign.Center) Modifier.fillMaxWidth() else Modifier
                    )
                )
            }
        },
        navigationIcon = {
            startButtonIcon?.let { icon ->
                onStartButtonClick?.let { onClick ->
                    IconButton(modifier = Modifier, onClick = onClick) {
                        Icon(
                            imageVector = icon,
                            contentDescription = "Botón de inicio"
                        )
                    }
                }
            }
        },
        actions = {
            endButtonIcon?.let { icon ->
                onEndButtonClick?.let { onClick ->
                    BadgedBox(
                        badge = {
                            Row {
                                AnimatedVisibility(
                                    visible = showNotificationBadge,
                                    enter = fadeIn(animationSpec = tween(durationMillis = 300)),
                                    exit = fadeOut(animationSpec = tween(durationMillis = 300))
                                ) {
                                    Badge(
                                        modifier = Modifier.offset(y = (0).dp, x = (-8).dp)
                                    ) { Text("!") }
                                }
                            }
                        }
                    ) {
                        IconButton(onClick = onClick) {
                            Icon(
                                imageVector = icon,
                                contentDescription = "Botón final"
                            )
                        }
                    }
                }
            }
        },
        modifier = modifier.background(MaterialTheme.colorScheme.primary)
    )
}

@Preview(showBackground = true)
@Composable
fun AppToolbarPreview() {
    Column {
        // Ejemplo de pantalla principal con menú y notificaciones
        AppToolbar(
            title = "Mi Aplicación",
            titleAlignment = TextAlign.Center,
            startButtonIcon = Icons.Default.Menu,
            onStartButtonClick = { /* Abrir menú */ },
            endButtonIcon = Icons.Default.Notifications,
            onEndButtonClick = { /* Ir a notificaciones */ },
            showNotificationBadge = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Ejemplo de pantalla de detalle con botón de regreso
        AppToolbar(
            title = "Detalle del Artículo",
            titleAlignment = TextAlign.Start,
            startButtonIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onStartButtonClick = { /* Volver atrás */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Ejemplo de pantalla sin botones laterales
        AppToolbar(
            title = "Configuración",
            titleAlignment = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Ejemplo con botón derecho sin badge
        AppToolbar(
            title = "Perfil de Usuario",
            titleAlignment = TextAlign.Start,
            endButtonIcon = Icons.Default.Menu,
            onEndButtonClick = { /* Abrir menú de perfil */ }
        )
    }
}