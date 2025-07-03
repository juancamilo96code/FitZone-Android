package com.vamaju.fitzone.presentation.commons

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vamaju.fitzone.presentation.navigation.Home
import com.vamaju.fitzone.presentation.navigation.MyClasses
import com.vamaju.fitzone.presentation.navigation.NavigationWrapper
import com.vamaju.fitzone.presentation.navigation.Notifications
import kotlinx.coroutines.launch

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
@Composable
fun MainScreen(navHostController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRouteWithParams = navBackStackEntry?.destination?.route
    val currentRoute = currentRouteWithParams?.substringBefore("?")?.substringBefore("/{")

    val toggleDrawerMenu: () -> Unit = {
        scope.launch {
            drawerState.apply {
                if (isClosed) open() else close()
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = currentRoute == Home.serializer().descriptor.serialName,
        drawerContent = {

            ModalDrawerSheet {
                Text("Menú de Navegación", modifier = Modifier.padding(16.dp))

                NavigationDrawerItem(
                    label = { Text("Notificaciones") },
                    selected = false,
                    onClick = {
                        toggleDrawerMenu()
                        navHostController.navigate(Notifications) {
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Mis Clases") },
                    selected = false,
                    onClick = {
                        toggleDrawerMenu
                        navHostController.navigate(MyClasses) {
                        }
                    }
                )
            }
        }
    ) {
        NavigationWrapper(
            navHostController = navHostController,
            openMenuDrawer = { toggleDrawerMenu() }
        )
    }
}