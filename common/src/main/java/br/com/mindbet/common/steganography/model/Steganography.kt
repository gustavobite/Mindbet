package br.com.mindbet.common.steganography.model

import android.graphics.Bitmap
import android.net.Uri


class Steganography {

    var message: String? = null
    var encoded_image: Bitmap? = null
    var encoded: Boolean = false
    var decoded: Boolean = false
    var uri: Uri? = null

    init {
        this.encoded_image = Bitmap.createBitmap(600, 600, Bitmap.Config.ARGB_8888)
    }


}
