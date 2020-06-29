package br.com.mindbet.common.steganography.interactor

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.steganography.model.Steganography
import br.com.mindbet.common.steganography.utils.EncodeHelper
import br.com.mindbet.common.steganography.utils.ImageUtils

class SteganographyEncodeImpl(
        private val context: Context,
        private val encodeHelper : EncodeHelper
) : SteganographyEncode() {

    override suspend fun execute(params: String): Resource<Steganography> {
        return try {
            val bitmap = getBitmapFromAsset("original.png")

            params.let { message ->

                val originalHeight = bitmap.height
                val originalWidth = bitmap.width

                val scrList = ImageUtils.splitImage(bitmap)

                val encodedList = encodeHelper.encodeMessage(scrList, message)

                scrList.forEach { it.recycle() }

                val srcEncoded = ImageUtils.mergeImage(encodedList, originalHeight, originalWidth)

                Resource.success(Steganography().also {
                    it.encoded_image = srcEncoded
                    it.encoded = true
                })
            }

        } catch (e: Exception) {
            Resource.error(e)
        }

    }

    private fun getBitmapFromAsset(path: String): Bitmap =
            context.assets.open(path).use { BitmapFactory.decodeStream(it) }


}