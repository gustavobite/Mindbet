package br.com.mindbet.common.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.text.Normalizer


infix fun <T> Boolean.then(param: T): T? = if (this) param else null

fun String.normalize(): String {
    val clearString = this.toLowerCase().capitalize()
    val original = arrayOf("ę", "š", "ã", "á", "é", "ê")
    val normalized = arrayOf("e", "s", "a", "a", "e", "e")

    return clearString.map {
        val index = original.indexOf(it.toString())
        if (index >= 0) normalized[index] else it
    }.joinToString("")
}

fun Context.isLollipopOrAbove(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

fun String.pathToBase64(): String {
    val byteArrayOS = ByteArrayOutputStream()
    val decodedFile = BitmapFactory.decodeFile(this)
    val sizeof = Math.min(decodedFile.byteCount / 1024 / 1024, 30)
    decodedFile.compress(Bitmap.CompressFormat.JPEG, 100-sizeof, byteArrayOS)

    return "data:image/jpeg;base64,${Base64.encodeToString(byteArrayOS.toByteArray(), Base64.NO_WRAP)}"
}

fun ByteArray.videoToBase64(): String {
    return "data:video/mp4;base64,${Base64.encodeToString(this, Base64.NO_WRAP)}"
}

@SuppressLint("SimpleDateFormat")
fun String.toTimestamp(): String {
    return try {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy")
        val outputFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = inputFormat.parse(this)
        outputFormat.format(date)
    } catch (e: Exception) {
        this
    }
}

@SuppressLint("SimpleDateFormat")
fun ViewModel.currentTime():String{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        current.format(formatter)
    } else {
        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        formatter.format(date)
    }
}

fun String.capitalizeWords(): String = toLowerCase().split(" ").map { it.capitalize() }.joinToString(" ")
fun <T> ArrayList<T>?.isNullOrEmpty(): Boolean = this?.let { it.size == 0 } ?: true
fun <T> ArrayList<T>?.hasNext(actualIndex: Int): T? {
    val indexNext = actualIndex + 1
    return try {
        this?.get(indexNext)
    } catch (e: IndexOutOfBoundsException) {
        null
    }
}

@SuppressLint("SimpleDateFormat")
fun String.formatDate(oldFormat: String, newFormat: String): String {
    var date = this

    try {
        var spf = SimpleDateFormat(oldFormat)
        val newDate = spf.parse(date)
        spf = SimpleDateFormat(newFormat)
        date = spf.format(newDate)
    } catch (e: Exception) {
        Log.e("", e.message)
    }
    return date
}

public fun CharSequence?.isNotNullOrEmpty(): Boolean {
    return !this.isNullOrEmpty()
}

public fun CharSequence?.hasMinLenght(minLenght : Int): Boolean {
    val tamanho = this?.length ?: 0
    return tamanho >= minLenght
}

fun <T> MutableLiveData<T>.clear() {
    value = null
}

fun String.addMask(mask: String): String {
    var formatado = ""
    var i = 0
    for (m in mask.toCharArray()) {
        if (m != '#') { // se não for um #, vamos colocar o caracter informado na máscara
            formatado += m
            continue
        }
        // Senão colocamos o valor que será formatado
        try {
            formatado += this[i]
        } catch (e: Exception) {
            break
        }

        i++
    }
    return formatado
}
private val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()

fun CharSequence.unaccent(): String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return REGEX_UNACCENT.replace(temp, "")
}