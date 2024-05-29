package com.example.timetonic.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.timetonic.view.screens.LandingDetailScreen
import com.example.timetonic.view.screens.LandingScreen
import com.example.timetonic.view.screens.LoginScreen


@Composable
fun TimeTonicApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = TimeTonicScreen.Login.route
    ) {
        composable(TimeTonicScreen.Login.route) {
            LoginScreen(
                navigateToLandingPage = { navController.navigate(TimeTonicScreen.Landing.route) }
            )
        }
        composable(TimeTonicScreen.Landing.route) {
            LandingScreen(
                navigateToBookDetail = { bookId ->
                    navController.navigate("${TimeTonicScreen.Detail.route}/$bookId")
                }
            )
        }
        composable(
            route = "${TimeTonicScreen.Detail.route}/{${TimeTonicScreen.BookId.route}}",
            arguments = listOf(navArgument(TimeTonicScreen.BookId.route) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val bookCode =
                requireNotNull(backStackEntry.arguments?.getString(TimeTonicScreen.BookId.route))
            LandingDetailScreen(bookCode = bookCode)
        }
    }
}