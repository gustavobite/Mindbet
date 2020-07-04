package br.com.mindbet.navigation

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections

/**
 * A simple sealed class to handle more properly
 * navigation from a [ViewModel]
 */
sealed class NavigationCommand {
    data class To(val directions: NavDirections): NavigationCommand()
    data class ToDestination(val destinationId: Int) : NavigationCommand()
    object Back: NavigationCommand()
    data class BackTo(val destinationId: Int, val inclusive: Boolean): NavigationCommand()
}