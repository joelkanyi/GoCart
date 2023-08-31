package io.devbits.gocart.services.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.devbits.gocart.services.ui.ServicesScreen

const val servicesRoute = "services"

fun NavController.navigateToServices(navOptions: NavOptions? = null) {
    this.navigate(servicesRoute, navOptions)
}

fun NavGraphBuilder.servicesScreen() {
    composable(route = servicesRoute) {
        ServicesScreen()
    }
}
