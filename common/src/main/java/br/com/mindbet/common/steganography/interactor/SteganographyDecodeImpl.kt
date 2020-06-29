package br.com.mindbet.common.steganography.interactor


import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.steganography.model.Steganography
import br.com.mindbet.common.steganography.utils.DecodeHelper
import br.com.mindbet.common.steganography.utils.ImageUtils

class SteganographyDecodeImpl(private val decodeHelper: DecodeHelper) : SteganographyDecode() {

    override suspend fun execute(params: Steganography): Resource<Steganography> {
        return try {
            val srcEncodedList = ImageUtils.splitImage(params.encoded_image ?: throw NullPointerException())
            val decodeMessage =
                    decodeHelper.decodeMessage(srcEncodedList) ?: throw NullPointerException()

            srcEncodedList.forEach { it.recycle() }

            Resource.success(params.also {
                it.message = decodeMessage
                it.decoded = true
            })


        } catch (e: Exception) {
            Resource.error(e)
        }
    }
}