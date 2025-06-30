package com.vamaju.fitzone.presentation.commons

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.vamaju.fitzone.presentation.navigation.MyClasses
import com.vamaju.fitzone.presentation.navigation.NavigationWrapper
import com.vamaju.fitzone.presentation.navigation.Notifications
import kotlinx.coroutines.launch

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
@Composable
fun MainScreen(navHostController: NavHostController, auth: FirebaseAuth) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {

            ModalDrawerSheet {
                Text("Menú de Navegación", modifier = Modifier.padding(16.dp))

                NavigationDrawerItem(
                    label = { Text("Notificaciones") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navHostController.navigate(Notifications) {
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Mis Clases") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navHostController.navigate(MyClasses) {
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Suscripciones") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        NavigationWrapper(auth = auth, navHostController = navHostController)
    }
}