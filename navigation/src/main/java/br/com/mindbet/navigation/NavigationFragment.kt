package br.com.mindbet.navigation

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import br.com.mindbet.common.base.BaseFragment
import br.com.mindbet.common.extension.hideKeyboard
import org.koin.android.ext.android.inject

abstract class NavigationFragment : BaseFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeNavigation(getViewModel())
    }

    abstract fun getViewModel(): NavigationViewModel

    //handle virtual back button
    abstract fun canBack(): Boolean

    /**
     * Observe a [NavigationCommand] [Event] [LiveData].
     * When this [LiveData] is updated, [Fragment] will navigate to its destination
     */
    private fun observeNavigation(viewModel: NavigationViewModel) {
        viewModel.navigation.observe(viewLifecycleOwner, Observer { event ->
            event?.getContentIfNotHandled()?.let { command ->
                activity?.hideKeyboard()
                when (command) {
                    is NavigationCommand.To -> findNavController().navigate(command.directions, getExtras())
                    is NavigationCommand.Back -> findNavController().navigateUp()
                    is NavigationCommand.BackTo -> findNavController().popBackStack(command.destinationId, command.inclusive)
                    is NavigationCommand.ToDestination -> findNavController().navigate(command.destinationId)
                }
            }
        })
    }

    /**
     * [FragmentNavigatorExtras] mainly used to enable Shared Element transition
     */
    open fun getExtras(): FragmentNavigator.Extras = FragmentNavigatorExtras()

    override fun getScreenName(): String = this.javaClass.canonicalName ?: ""
}