package br.com.mindbet.common.base

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.mindbet.common.R
import br.com.mindbet.common.helper.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.max


abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    companion object {
        const val ARG_REVEAL_CENTER_X = "ARG_REVEAL_CENTER_X"
        const val ARG_REVEAL_CENTER_Y = "ARG_REVEAL_CENTER_Y"
        const val X_TRANSACTION_ID_EXTRA = "X_TRANSACTION_ID_EXTRA"
    }

    abstract fun getScreenName(): String
    private var circularRevealView : View? = null
//    private val baseViewModel: BaseViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            observeGlobalLayout()
        }

    }


    protected fun openWhatsApp() {
        val contact = "+55 11 40040800"
        val url = "https://api.whatsapp.com/send?phone=$contact"
        val whatsAppPackage = "com.whatsapp"
        try {
            packageManager.getPackageInfo(whatsAppPackage, PackageManager.GET_ACTIVITIES)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        } catch (e: PackageManager.NameNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.whatsapp")
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
    }

    open fun appInForeground(): Boolean {
        val activityManager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcesses = activityManager.runningAppProcesses ?: return false
        return runningAppProcesses.any { it.processName == this.packageName && it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND }
    }


    private fun observeGlobalLayout() {
        this.findViewById<View>(android.R.id.content)?.let {
            val viewTreeObserver = it.viewTreeObserver
            if (viewTreeObserver.isAlive) viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    onReadyForEnterTransition()
                    if (circularRevealView != null) {
                        circularRevealActivity()
                    }
                    it.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }

    open fun onReadyForEnterTransition() {}

    fun showFragment(@IdRes containerIdRes: Int, fragment: Fragment, arguments: Bundle? = null, shouldAddToBackStack: Boolean = false, vararg sharedElements: View){
        arguments?.let {
            fragment.arguments = arguments
        }

        val transaction = supportFragmentManager.beginTransaction().apply {
            replace(containerIdRes, fragment)
            if (shouldAddToBackStack) addToBackStack(fragment.javaClass.name)
        }

        sharedElements.forEach {element ->
            if (!element.transitionName.isNullOrEmpty()) {
                transaction.addSharedElement(element, element.transitionName)
            }
        }

        transaction.commit()
    }

    fun enableCircularRevealTransition(view: View) {
        circularRevealView = view
    }

    fun disableCircularRevealTransition() {
        circularRevealView = null
    }

    private fun circularRevealActivity() {
        circularRevealView?.let { root ->

            val centerX = intent.getIntExtra(ARG_REVEAL_CENTER_X, root.width / 2)
            val centerY = intent.getIntExtra(ARG_REVEAL_CENTER_Y, root.height / 2)

            val finalRadius : Float = max(root.width, root.height).toFloat()

            // create the animator for this view (the start radius is zero)
            val circularReveal =
                ViewAnimationUtils.createCircularReveal(root, centerX, centerY, 0f, finalRadius)
            circularReveal.duration = 400

            // make the view visible and start the animation
            root.visibility = View.VISIBLE
            circularReveal.start()
        }

    }

    protected fun showToast(message: String, time: Int = Toast.LENGTH_SHORT){
        Toast.makeText(this, message, time).show()
    }






}

