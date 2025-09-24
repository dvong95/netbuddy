package com.vongda.netbuddy

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vongda.netbuddy.data.viewmodels.MatchViewModel
import com.vongda.netbuddy.ui.home.HomeScreen
import com.vongda.netbuddy.ui.instructions.InstructionsScreen
import com.vongda.netbuddy.ui.matchsettings.MatchSettingsScreen
import com.vongda.netbuddy.ui.match.MatchScreen
import com.vongda.netbuddy.ui.pointsignify.PointScreen
import com.vongda.netbuddy.ui.results.ResultsScreen
import kotlinx.serialization.Serializable

@Serializable object HomeScreen
@Serializable object InstructionsScreen
@Serializable object MatchSettingsScreen
@Serializable object MatchScreen
@Serializable object ResultsScreen
@Serializable object PointScreen

@Composable
fun App(navController: NavHostController, modifier: Modifier = Modifier) {
    val vm: MatchViewModel = viewModel()
    NavHost (
        navController = navController,
        startDestination = HomeScreen,
        modifier = modifier
    ) {
        composable<HomeScreen> {
            HomeScreen(
                navigateToMatchSettings = { navController.navigate(route = MatchSettingsScreen)},
                navigateToInstructions = { navController.navigate(route = InstructionsScreen)}
            )
        }

        // Instructions for initial setup
        composable<InstructionsScreen> {
            InstructionsScreen()
        }

        composable<MatchSettingsScreen> {
            MatchSettingsScreen(
                vm,
                navigateToMatch = { navController.navigate(route = MatchScreen)}
            )
        }

        composable<MatchScreen> {
            MatchScreen(
                vm,
                navigateToPointScreen = { navController.navigate(route = PointScreen)},
                navigateToResults = { navController.navigate(route = ResultsScreen)},
                navigateToMatchSettings = { navController.navigate(route = MatchSettingsScreen)}
            )
        }

        composable<PointScreen> {
            PointScreen(
                vm,
                navigateToMatch = { navController.navigate(route = MatchScreen)},
                navigateToResults = { navController.navigate(route = ResultsScreen)}
            )
        }

        composable<ResultsScreen> {
            ResultsScreen(
                vm,
                navigateToHome = { navController.navigate(route = HomeScreen)},
                navigateToMatch = { navController.navigate(route = MatchScreen)}
            )
        }


    }
}