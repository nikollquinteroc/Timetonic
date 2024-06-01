package com.example.timetonic.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.timetonic.ui.navigation.TimeTonicScreens.B_C
import com.example.timetonic.ui.navigation.TimeTonicScreens.Detail
import com.example.timetonic.ui.navigation.TimeTonicScreens.Landing
import com.example.timetonic.ui.navigation.TimeTonicScreens.Login
import com.example.timetonic.ui.navigation.TimeTonicScreens.O_U
import com.example.timetonic.ui.navigation.TimeTonicScreens.SessionKey
import com.example.timetonic.ui.screens.detail.LandingDetailScreen
import com.example.timetonic.ui.screens.landing.LandingScreen
import com.example.timetonic.ui.screens.login.LoginScreen


@Composable
fun TimeTonicApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Login.route
    ) {
        composable(Login.route) {
            LoginScreen(
                navigateToLandingPage = { loginResponse ->
                    navController.navigate("${Landing.route}/${loginResponse.sesskey}/${loginResponse.o_u}")
                }
            )
        }
        composable(
            route = "${Landing.route}/{${SessionKey.route}}/{${O_U.route}}",
            arguments = listOf(
                navArgument(SessionKey.route) { type = NavType.StringType },
                navArgument(O_U.route) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val sesskey = requireNotNull(backStackEntry.arguments?.getString(SessionKey.route))
            val o_u = requireNotNull(backStackEntry.arguments?.getString(O_U.route))

            LandingScreen(
                navigateToBookDetail = { bookResponse ->
                    navController.navigate("${Detail.route}/${bookResponse.sesskey}/${bookResponse.o_u}/${bookResponse.b_c}")
                },
                navigateToLogin = {
                    navController.navigate(Login.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                sesskey = sesskey,
                o_u = o_u
            )
        }
        composable(
            route = "${Detail.route}/{${SessionKey.route}}/{${O_U.route}}/{${B_C.route}}",
            arguments = listOf(
                navArgument(SessionKey.route) { type = NavType.StringType },
                navArgument(O_U.route) { type = NavType.StringType },
                navArgument(B_C.route) { type = NavType.StringType }
            )
        ) {
            LandingDetailScreen(
                navigateToLogin = {
                    navController.navigate(Login.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}