package com.vamaju.fitzone.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.google.firebase.auth.FirebaseAuth
import com.vamaju.fitzone.presentation.book_class.BookClassScreen
import com.vamaju.fitzone.presentation.class_details.ClassTypeDetailsScreen
import com.vamaju.fitzone.presentation.home.HomeScreen
import com.vamaju.fitzone.presentation.login_email.LoginScreen
import com.vamaju.fitzone.presentation.notification.NotificationsScreen
import com.vamaju.fitzone.presentation.register_email.RegisterScreen
import com.vamaju.fitzone.presentation.scheduled_classes.BookedClassesScreen

/**
 * @author Juan Camilo Collantes Tovar on 27/06/2025
 * **/
@Composable
fun NavigationWrapper(navHostController: NavHostController, authViewModel: AuthViewModel = hiltViewModel()) {

    val startDestination by authViewModel.startDestination.collectAsState()

    if (startDestination.isBlank()) {
        // Mostrar una pantalla de carga mientras se decide la ruta de inicio
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        NavHost(
            navController = navHostController,
            startDestination = if (startDestination == "Home") Home else Login
        ) {

            composable<Login>() {
                LoginScreen(navController = navHostController)
            }

            composable<Register>() {
                RegisterScreen(navController = navHostController)
            }

            composable<Home>() {
                HomeScreen(
                    navigateToDetail = { classTypeId ->
                        navHostController.navigate(
                            ClassTypeDetails(classTypeId = classTypeId)
                        )
                    }
                )
            }

            composable<ClassTypeDetails> { backStackEntry ->
                val classTypeDetails: ClassTypeDetails = backStackEntry.toRoute()
                ClassTypeDetailsScreen(
                    classTypeId = classTypeDetails.classTypeId,
                    navigateToBookClass = { id ->
                        navHostController.navigate(BookClass(id))
                    }
                ) {}

            }

            composable<BookClass>() { backStackEntry ->
                val bookClass: BookClass = backStackEntry.toRoute()
                BookClassScreen(
                    classId = bookClass.classId
                ) {

                }
            }

            composable<MyClasses> {
                BookedClassesScreen(
                    navigateToBookClass = {
                        navHostController.navigate(BookClass(it))
                    },
                    onClose = {})
            }

            composable<Payments>() {

            }

            composable<Notifications>() {
                NotificationsScreen { }
            }
        }
    }
}