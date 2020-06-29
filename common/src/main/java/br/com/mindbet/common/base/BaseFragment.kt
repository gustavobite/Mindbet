package br.com.mindbet.common.base

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import br.com.mindbet.common.extension.valueOrZeroIfNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.koin.android.ext.android.inject
import kotlin.math.abs

const val deltaYSwipe = 75

abstract class BaseFragment : androidx.fragment.app.Fragment(), CoroutineScope by MainScope() {

    abstract fun getScreenName() : String
    abstract fun layoutResource() : Int

    private var lastBeginY: Float? = null
    private var lastEndY: Float? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            observeViewGlobalLayout(view)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun observeViewGlobalLayout(view: View) {
        val viewTreeObserver = view.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    this@BaseFragment.onGlobalLayout(view)
                    view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }

    fun showKeyboard(view: View) {
        if (!isDetached) {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun hideKeyboard(view: View) {
        if (!isDetached) {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun <T:View> findViewById(@IdRes id: Int) : T? = try { view?.findViewById(id) } catch(err : Throwable) { null }

    open fun onGlobalLayout(root: View) { onReadyForEnterTransition() }

    open fun onReadyForEnterTransition() { }

    protected fun showToast(message: String, time: Int = Toast.LENGTH_SHORT){
        Toast.makeText(requireContext(), message, time).show()
    }
}