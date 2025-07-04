package com.vamaju.fitzone.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vamaju.fitzone.presentation.commons.composables.search_bar.SearchBar
import com.vamaju.fitzone.presentation.commons.composables.topbar.AppToolbar
import com.vamaju.fitzone.presentation.home.composables.ClassTypeCardItem
import com.vamaju.fitzone.ui.theme.FitZoneTheme

/**
 * @author Juan Camilo Collantes Tovar on 28/06/2025
 * **/

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit,
    navigateToNotifications: () -> Unit,
    openMenuDrawer: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(

        topBar = {
            AppToolbar(
                title = "Mi Aplicación",
                titleAlignment = TextAlign.Center,
                startButtonIcon = Icons.Default.Menu,
                onStartButtonClick = openMenuDrawer,
                endButtonIcon = Icons.Default.Notifications,
                onEndButtonClick = navigateToNotifications,
                showNotificationBadge = true
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            SearchBar(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                placeholder = "Find a class",
                value = uiState.searchQuery,
                onValueChange = viewModel::onSearchQueryChanged
            )

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error: ${uiState.errorMessage}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                uiState.classTypes.isEmpty() && uiState.searchQuery.isNotEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No se encontraron resultados para '${uiState.searchQuery}'")
                    }
                }

                uiState.classTypes.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No hay clases disponibles en este momento.")
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        item {
                            Text(
                                text = "Próximas Clases",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }

                        items(uiState.classTypes) { classType ->
                            ClassTypeCardItem(
                                item = classType,
                                onClick = { navigateToDetail(classType.id) })
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    FitZoneTheme {
        HomeScreen(
            navigateToDetail = {},
            navigateToNotifications = {},
            openMenuDrawer = {}
        )
    }
}