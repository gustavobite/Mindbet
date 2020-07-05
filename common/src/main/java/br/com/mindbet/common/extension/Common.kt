package br.com.mindbet.common.extension

import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.graphics.Typeface
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.format.Formatter
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.View.GONE
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.set
import androidx.core.text.toSpannable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import br.com.mindbet.common.R
import br.com.mindbet.common.component.error.ErrorComponent
import com.airbnb.lottie.LottieAnimationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.Normalizer
import java.util.*


inline fun <reified T> String.createObject(): T =
    Gson().fromJson<T>(this, object : TypeToken<T>() {}.type)


fun createUUID(withHifen: Boolean = true): String {
    var uuid = UUID.randomUUID()
        .toString()
    if (!withHifen) uuid = uuid.replace("-", "")
    return uuid
}


fun String.fromHtml(): Spanned {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT)
}


fun View.expandOrCollapse(parent: ViewGroup, image: ImageView) {

    val expand = visibility == GONE
    val transition = ChangeBounds().apply {
        duration = 200L
        interpolator = AccelerateDecelerateInterpolator()
    }
    TransitionManager.beginDelayedTransition(parent, transition)
    image.setImageResource(
        R.drawable.ic_expand_less_24dp.takeIf { expand } ?: R.drawable.ic_expand_more_24dp
    )
    this.visibility = View.VISIBLE.takeIf { expand } ?: GONE
}

fun NavController.navigateSafe(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navExtras: Navigator.Extras? = null
) {
    val action = currentDestination?.getAction(resId)
    if (action != null) navigate(resId, args, navOptions, navExtras)
}


fun Activity.onKeyboardShownListener(listener: (Boolean) -> Unit) {
    val KEYBOARD_MIN_HEIGHT_RATIO = 0.15
    val view = findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
    view.viewTreeObserver.addOnGlobalLayoutListener {
        val r = Rect()
        view.getWindowVisibleDisplayFrame(r)
        val screenHeight = view.rootView.height
        val heightDiff = screenHeight - r.height()
        val isOpen = heightDiff > screenHeight * KEYBOARD_MIN_HEIGHT_RATIO
        listener(isOpen)
    }
}

fun Context.dp(value: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value.toFloat(),
        resources.displayMetrics
    ).toInt()
}

fun Context.getDimension(@DimenRes resource: Int): Float {
    return resources.getDimension(resource)
}

fun Context.loadCustomFont(fontName: String) : Typeface{
    return Typeface.createFromAsset(this.assets,fontName)
}


fun String.removeAccents(): String {
    return Normalizer.normalize(this, Normalizer.Form.NFD)
        .replace("\\p{InCombiningDiacriticalMarks}+", "")
}

fun String.search(
    what: String,
    ignoreCase: Boolean = true,
    ignoreAccents: Boolean = true
): Boolean {
    return if (ignoreAccents)
        this.removeAccents().contains(what.removeAccents(), ignoreCase)
    else
        this.contains(what, ignoreCase)

}

fun LottieAnimationView.doOnFinish(callback: (Animator?) -> Unit) {
    addAnimatorListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {}

        override fun onAnimationEnd(animation: Animator?) {
            callback(animation)
        }

        override fun onAnimationCancel(animation: Animator?) {}

        override fun onAnimationStart(animation: Animator?) {}
    })
}

fun LottieAnimationView.doStartAndReapeat(callback: (Animator?) -> Unit) {
    addAnimatorListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
            callback(animation)
        }

        override fun onAnimationEnd(animation: Animator?) {
        }

        override fun onAnimationCancel(animation: Animator?) {}

        override fun onAnimationStart(animation: Animator?) {
            callback(animation)
        }
    })
}


fun TransitionSet.addListener(
    onStart: (Transition) -> Unit = {},
    onResume: (Transition) -> Unit = {},
    onPause: (Transition) -> Unit = {},
    onCancel: (Transition) -> Unit = {},
    onEnd: (Transition) -> Unit = {}
) {
    this.addListener(object : Transition.TransitionListener {
        override fun onTransitionStart(transition: Transition) = onStart(transition)
        override fun onTransitionResume(transition: Transition) = onResume(transition)
        override fun onTransitionPause(transition: Transition) = onPause(transition)
        override fun onTransitionCancel(transition: Transition) = onCancel(transition)
        override fun onTransitionEnd(transition: Transition) = onEnd(transition)
    })
}

val BARCODE_MASK_36_46 = "#####.##### #####.###### #####.###### # #############"
val BARCODE_MASK_47 = "#####.##### #####.###### #####.###### # ##############"
val BARCODE_MASK_48 = "############ ############ ############ ############"
val BARCODE_MASK_58 = "############ ############ ############ ######################"

fun String.maskText(mask: String?): String {
    var maskedText = ""
    if (!isNullOrEmpty() && !mask.isNullOrEmpty()) {
        var i = 0
        for (m in mask.toCharArray()) {
            if (m != '#') {
                maskedText += m
                continue
            }
            if(m == '_'){
                maskedText = "\n"
                continue
            }

            try {
                maskedText += toCharArray()[i]
            } catch (e: Exception) {
                break
            }
            println(maskedText)
            i++
            if (i >= this.length) break
        }
    }
    return maskedText
}


fun String?.unMaskText(): String? = this?.replace("[.]".toRegex(), "")?.replace("[-]".toRegex(), "")
    ?.replace("[/]".toRegex(), "")?.replace("[(]".toRegex(), "")
    ?.replace("[)]".toRegex(), "")?.replace("[ ]".toRegex(), "")?.replace("[\n]".toRegex(), "")

fun String?.isBarCode(): Boolean{
    return this?.replace(" ","")?.replace(".","")?.matches("[0-9]{36,46}|[0-9]{47}|[0-9]{48}".toRegex()) == true
}

fun String.validateBarcode(): String {
    var barcode = this
    when {
        barcode.length in 36..46 -> {
            barcode = barcode.padStart(48, '0')
        }
        barcode.length < 36 -> {
            throw Exception("Código de barras inválido, por favor consulte o emissor.")
        }
    }
    return barcode
}

/**
 *  EX: R$ 120,00
 *  set text R$ - small
 *  set text 120, - BIG
 *  set text 00 - small
 */
fun SpannableString.valueAmountSpanTransForm(context: Context) {
    setSpan(AbsoluteSizeSpan(16, true), 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    setSpan(AbsoluteSizeSpan(40, true), 2, this.length -2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    setSpan(AbsoluteSizeSpan(20, true), this.length -2, this.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.textColor)), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
}

fun SpannableString.valueAmountSpanTransFormWithoutCurrency() : SpannableString {
    setSpan(AbsoluteSizeSpan(40, true), 0, this.length -2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    setSpan(AbsoluteSizeSpan(20, true), this.length -2, this.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    return this
}

fun String.getInitials(): String {
    return this
        .split(Regex(" "))
        .map { it.first() }
        .joinToString("")
        .take(2)
        .toUpperCase()
}

fun Context.showErrorComponent(
    @StringRes bodyMessage : Int = R.string.error_try_again_later,
    @StringRes buttonText: Int = R.string.back,
    callback: (() -> Unit)? = null
) {
    ErrorComponent(this,bodyMessage,buttonText,callback).show()
}

fun Activity.hideKeyboard() {
    try {
        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
    } catch (e: Exception) {
        // Ignore exceptions if any
        Log.e("hideKeyboard", e.toString(), e)
    }

}

fun Context?.spToPx(sp: Float) : Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, this?.resources?.displayMetrics)


fun Context.getDeviceId(): String = try {
    Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
} catch (e: Exception) {
    e.printStackTrace()
    ""
}

inline fun <reified T> String.jsonToObject() : T{
    return Gson().fromJson(this,T::class.java)
}

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
fun Activity.statusBarColor(color:Int){
    window.statusBarColor = color
}

@RequiresApi(api = Build.VERSION_CODES.M)
fun Activity.lightStatusBar(){
    window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}

@RequiresApi(api = Build.VERSION_CODES.M)
fun Activity.clearStatusBar(){
    window.decorView.systemUiVisibility = 0
}
