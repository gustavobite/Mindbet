package br.com.mindbet.common.steganography.utils

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import java.nio.charset.Charset
import java.util.*

class EncodeHelper {
    private val TAG = EncodeHelper::class.java.name

    private val END_MESSAGE_COSTANT = "#!@"
    private val START_MESSAGE_COSTANT = "@!#"
    private val binary = intArrayOf(16, 8, 0)
    private val toShift = intArrayOf(6, 4, 2, 0)


    private fun encodeMessage(integerPixelArray: IntArray, imageColumns: Int, imageRows: Int, messageEncodingStatus: MessageEncodingStatus): ByteArray {

        val channels = 3
        var shiftIndex = 4
        val result = ByteArray(imageRows * imageColumns * channels)
        var resultIndex = 0

        for (row in 0 until imageRows) {
            for (col in 0 until imageColumns) {
                val element = row * imageColumns + col
                var tmp: Byte

                for (channelIndex in 0 until channels) {
                    if (!messageEncodingStatus.isMessageEncoded) {
                        tmp =
                            (integerPixelArray[element] shr binary[channelIndex] and 0xFF and 0xFC or (messageEncodingStatus.byteArrayMessage!![messageEncodingStatus.currentMessageIndex].toInt() shr toShift[shiftIndex++ % toShift.size] and 0x3)).toByte()

                        if (shiftIndex % toShift.size == 0) {
                            messageEncodingStatus.incrementMessageIndex()
                        }

                        if (messageEncodingStatus.currentMessageIndex == messageEncodingStatus.byteArrayMessage?.size) {
                            messageEncodingStatus.setMessageEncoded()
                        }
                    } else {
                        tmp =
                            (integerPixelArray[element] shr binary[channelIndex] and 0xFF).toByte()
                    }
                    result[resultIndex++] = tmp
                }
            }
        }
        return result
    }

    fun encodeMessage(splitedImages: List<Bitmap>, encptMessage: String): List<Bitmap> {
        var encryptedMessage = encptMessage

        val result = ArrayList<Bitmap>(splitedImages.size)

        encryptedMessage += END_MESSAGE_COSTANT
        encryptedMessage = START_MESSAGE_COSTANT + encryptedMessage

        val byteEncryptedMessage = encryptedMessage.toByteArray(Charset.forName("ISO-8859-1"))
        val message = MessageEncodingStatus(byteEncryptedMessage)

        Log.i(TAG, "Message length " + byteEncryptedMessage.size)

        for (bitmap in splitedImages) {

            if (!message.isMessageEncoded) {
                val width = bitmap.width
                val height = bitmap.height

                val oneD = IntArray(width * height)
                bitmap.getPixels(oneD, 0, width, 0, 0, width, height)

                val density = bitmap.density
                val encodedImage = encodeMessage(oneD, width, height, message)

                val oneDMod = ImageUtils.byteArrayToIntArray(encodedImage)

                val encodedBitmap = Bitmap.createBitmap(
                    width, height,
                    Bitmap.Config.ARGB_8888
                )
                encodedBitmap.density = density

                var masterIndex = 0

                for (j in 0 until height)
                    for (i in 0 until width) {
                        encodedBitmap.setPixel(
                            i, j, Color.argb(
                                0xFF,
                                oneDMod[masterIndex] shr 16 and 0xFF,
                                oneDMod[masterIndex] shr 8 and 0xFF,
                                oneDMod[masterIndex++] and 0xFF
                            )
                        )
                    }
                result.add(encodedBitmap)
            } else {
                result.add(bitmap.copy(bitmap.config, false))
            }
        }
        return result
    }

    private class MessageEncodingStatus internal constructor(byteArrayMessage: ByteArray) {
        internal var isMessageEncoded: Boolean = false
            private set
        internal var currentMessageIndex: Int = 0
        internal var byteArrayMessage: ByteArray? = null

        init {
            this.isMessageEncoded = false
            this.currentMessageIndex = 0
            this.byteArrayMessage = byteArrayMessage
        }

        internal fun incrementMessageIndex() {
            currentMessageIndex++
        }

        internal fun setMessageEncoded() {
            this.isMessageEncoded = true
        }
    }
}
