package br.com.mindbet.common.extension

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.method.DigitsKeyListener
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import br.com.mindbet.common.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.AppBarLayout

fun View.changeVisibility(){
    visibility = if(visibility == View.GONE) View.VISIBLE else View.GONE
}
fun View.isKeyboardOpen(): Boolean {
    val r = Rect()
    getWindowVisibleDisplayFrame(r)
    val screenHeight = rootView.height
    val keypadHeight = screenHeight - r.bottom
    return keypadHeight > screenHeight * 0.15
}

fun View.addKeyboardChangeListener(listener: (isOpened: Boolean) -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener {
        listener.invoke(isKeyboardOpen())
    }
}


fun View.isHidden() = visibility == View.GONE || visibility == View.INVISIBLE
fun View.hide() {
    this.visibility = View.GONE
}

fun View.dismiss() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.showDelayed(){
    TransitionManager.beginDelayedTransition(this.rootView as ViewGroup)
    show()
}

fun View.dismissDelayed(){
    TransitionManager.beginDelayedTransition(this.rootView as ViewGroup)
    dismiss()
}

fun View.changeColor(color: String) {
    this.setBackgroundColor(Color.parseColor(color))
}

fun EditText.trimText() = text.toString().trim()
fun EditText.getString() = text.toString()

fun String.unmask() = this.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "")
    .replace("[/]".toRegex(), "").replace("[(]".toRegex(), "")
    .replace("[)]".toRegex(), "")
    .replace(" ", "")

inline fun <T> T.guard(rule: Boolean, call: () -> Unit): T? {
    return if (rule) this
    else {
        call()
        null
    }
}

inline fun EditText.guard(rule: Boolean, call: () -> Unit): String? {
    return if (rule) text.toString()
    else {
        call()
        null
    }
}

inline fun TextInputLayout.guard(rule: Boolean, call: () -> Unit): String? {
    return if (rule) editText?.text.toString()
    else {
        call()
        null
    }
}

fun TextView.bold(resource: String, targetWord: String, fontPath: String) {
    val spannableTitle = SpannableString(resource)
    val startOffset = spannableTitle.indexOf(targetWord)
    val endOffset = startOffset + targetWord.length

    spannableTitle.apply {
        setSpan(TypefaceSpan(fontPath), startOffset, endOffset, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        this@bold.text = this
    }
}

fun SpannableString.bold(targetWord: String, color: String) {
    val spannableTitle = this
    val startOffset = spannableTitle.indexOf(targetWord)
    val endOffset = startOffset + targetWord.length

    spannableTitle.apply {
        setSpan(
            ForegroundColorSpan(Color.parseColor(color)),
            startOffset,
            endOffset,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        setSpan(
            StyleSpan(Typeface.BOLD),
            startOffset,
            endOffset,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }
}

fun SpannableString.setLinkSpan(context: Context, text: String, url: String) {
    val textIndex = this.indexOf(text)
    setSpan(
        object : ClickableSpan() {
            override fun onClick(widget: View) {
                Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(url) }
                    .also { context.startActivity(it) }
            }
        },
        textIndex,
        textIndex + text.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    this.bold(text, "#75ae50")
}

fun SpannableString.setLinkSpan(context: Context, text: String, url: String, callback: () -> Unit) {
    val textIndex = this.indexOf(text)
    setSpan(
        object : ClickableSpan() {
            override fun onClick(widget: View) {
                Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(url) }
                    .also { context.startActivity(it) }
                callback()
            }
        },
        textIndex,
        textIndex + text.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    this.bold(text, "#75ae50")
}

fun SpannableString.setClickableSpan(text: String, callback: () -> Unit) {
    val textIndex = this.indexOf(text)
    setSpan(
        object : ClickableSpan() {
            override fun onClick(widget: View) {
                callback()
            }
        },
        textIndex,
        textIndex + text.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    this.bold(text, "#75ae50")
}

fun TextInputLayout.equalsText(other: TextInputLayout): Boolean {
    return this.editText?.text?.toString().equals(other.editText?.text?.toString())
}

fun EditText.text(): String = this.text.toString()

fun EditText.setOnlyLetters() {
    keyListener =
        DigitsKeyListener.getInstance("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZZ âãàáäêèéëîìíïôòóõöùúüñçÂÃÀÁÄÊÈÉËÎÌÍÏÔÒÓÕÖÙÚÜÑÇ")
}

fun TextInputLayout.setOnlyLetters() {
    editText?.setOnlyLetters()
}

fun EditText.setDigits() {
    keyListener =
        DigitsKeyListener.getInstance("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZZ âãàáäêèéëîìíïôòóõöùúüñçÂÃÀÁÄÊÈÉËÎÌÍÏÔÒÓÕÖÙÚÜÑÇ1234567890")
}

fun TextInputLayout.setDigits() {
    editText?.setDigits()
}

fun NestedScrollView.scrollTo(view: TextInputEditText) {
    smoothScrollTo(0, view.top)
}

fun NestedScrollView.scrollTo(view: View) {
    smoothScrollTo(0, view.bottom)
}

fun TextInputLayout.rangeValueDelete() {
    this.setEndIconOnClickListener {
        this@rangeValueDelete.editText?.setText("0")
    }
}

fun TextInputLayout.disable() {
    error = null
    isEnabled = false
    editText?.setText("")
}

fun View.convertToA4PDFBitmap(): Bitmap {
    val measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    measure(measureSpec, measureSpec)
    Log.d("TAG","" + layoutParams.height)
    Log.d("TAG","" + height)
    layout(0, 0, 590, 1500)
    val bitmap = Bitmap.createBitmap(590, 1500, Bitmap.Config.ARGB_8888)
    bitmap.eraseColor(Color.TRANSPARENT)
    val canvas = Canvas(bitmap)
    draw(canvas)
    return bitmap
}

fun Bitmap.convertToPDF(): PdfDocument {
    val document = PdfDocument()
    val pageInfo: PdfDocument.PageInfo =
        PdfDocument.PageInfo.Builder(width, height, 1).create()
    val page: PdfDocument.Page = document.startPage(pageInfo)

    // Draw the bitmap onto the page
    val canvas: Canvas = page.canvas
    canvas.drawBitmap(this, 0f, 0f, null)
    document.finishPage(page)
    return document
}


fun AppBarLayout.removeAllElevation(){
    this.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, _ -> ViewCompat.setElevation(this, 0F); })
}
fun View.bounce() {
    val bounceAnimation = AnimationUtils.loadAnimation(this.context, R.anim.bounce)
    this.startAnimation(bounceAnimation)
}

fun NestedScrollView.scrollToBottom() {
    val lastChild = getChildAt(childCount - 1)
    val bottom = lastChild.bottom + paddingBottom
    val delta = bottom - (scrollY+ height)
    smoothScrollBy(0, delta)
}

fun ImageView.showImage(url:String){
    Glide.with(this.context)
        .asBitmap()
        .load(url)
        .dontAnimate()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

@BindingAdapter("bind:loadImage")
fun loadImage(view : ImageView, url : String?){
    view.showImage(url ?: "")
}