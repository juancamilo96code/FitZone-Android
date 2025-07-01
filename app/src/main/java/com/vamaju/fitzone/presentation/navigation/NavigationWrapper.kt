package com.vamaju.fitzone.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.google.firebase.auth.FirebaseAuth
import com.vamaju.fitzone.presentation.book_class.BookClassScreen
import com.vamaju.fitzone.presentation.class_details.ClassTypeDetailsScreen
import com.vamaju.fitzone.presentation.home.HomeScreen
import com.vamaju.fitzone.presentation.login_email.LogInEmailScreen
import com.vamaju.fitzone.presentation.register_email.RegisterEmailScreen

/**
 * @author Juan Camilo Collantes Tovar on 27/06/2025
 * **/
@Composable
fun NavigationWrapper(navHostController: NavHostController, auth: FirebaseAuth) {

    NavHost(
        navController = navHostController,
        startDestination = if (auth.currentUser != null) Home else Home
    ) {
        composable<Splash> {

        }

        composable<Login>() {
            LogInEmailScreen(
                auth = auth,
                navigateToHome = {
                    navHostController.navigate("home")
                },
                navigateToRegister = {
                    navHostController.navigate("Register")
                })
        }

        composable<Register>() {
            RegisterEmailScreen(
                auth,
                navigateToLogin = {
                    navHostController.navigate("Login")
                })
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
            BookClassScreen {

            }
        }

        composable<MyClasses> {

        }

        composable<Payments>() {

        }

        composable <Notifications>() {  }
    }

}