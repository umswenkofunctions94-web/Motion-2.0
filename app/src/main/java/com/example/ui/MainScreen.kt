package com.example.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.components.BookingRequestDialog
import com.example.ui.creators.AvailableCreatorsScreen
import com.example.ui.opportunities.OpportunitiesScreen
import com.example.ui.profile.ProfileScreen

@Composable
fun MainScreen(viewModel: MotionViewModel = viewModel()) {
    val navController = rememberNavController()

    val opportunities by viewModel.opportunities.collectAsStateWithLifecycle()
    val creators by viewModel.creators.collectAsStateWithLifecycle()
    val myProfile by viewModel.myProfile.collectAsStateWithLifecycle()
    val activeBookingRequest by viewModel.activeBookingRequest.collectAsStateWithLifecycle()

    val screens = listOf(
        Screen.Opportunities,
        Screen.Creators,
        Screen.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Opportunities.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Opportunities.route) {
                OpportunitiesScreen(opportunities)
            }
            composable(Screen.Creators.route) {
                AvailableCreatorsScreen(
                    creators = creators,
                    onBookCreator = { viewModel.initiateBooking(it) }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(profile = myProfile)
            }
        }

        activeBookingRequest?.let { request ->
            BookingRequestDialog(
                request = request,
                onAccept = { viewModel.resolveBooking(true) },
                onDecline = { viewModel.resolveBooking(false) }
            )
        }
    }
}

sealed class Screen(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Opportunities : Screen("opportunities", "Gigs", Icons.Default.LocalFireDepartment)
    object Creators : Screen("creators", "Available Now", Icons.Default.Star)
    object Profile : Screen("profile", "Reputation", Icons.Default.Person)
}
