package br.com.mindbet.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import br.com.mindbet.common.helper.Event

abstract class NavigationViewModel: ViewModel() {

    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> = _navigation

    /**
     * Convenient method to handle navigation from a [ViewModel]
     */
     fun navigate(directions: NavDirections) {
        _navigation.value = Event(NavigationCommand.To(directions))
    }

    fun navigate(destinationId: Int) {
        _navigation.value = Event(NavigationCommand.ToDestination(destinationId))
    }

    fun navigateBack() {
        _navigation.value = Event(NavigationCommand.Back)
    }

    fun backTo(destinationId: Int, inclusive: Boolean = false) {
        _navigation.value = Event(NavigationCommand.BackTo(destinationId, inclusive))
    }
}