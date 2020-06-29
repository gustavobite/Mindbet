package br.com.mindbet.common.steganography.utils

import android.graphics.Bitmap
import java.nio.charset.Charset
import java.util.*
import kotlin.experimental.or

class DecodeHelper {
    private val TAG = DecodeHelper::class.java.name
    private val END_MESSAGE_COSTANT = "#!@"
    private val START_MESSAGE_COSTANT = "@!#"
    private val andByte = byteArrayOf(0xC0.toByte(), 0x30, 0x0C, 0x03)
    private val toShift = intArrayOf(6, 4, 2, 0)


    private fun decodeMessage(bytePixelArray: ByteArray, messageDecodingStatus: MessageDecodingStatus) {
        val byteEncryptedMessage = Vector<Byte>()
        var shiftIndex = 4
        var tmp: Byte = 0x00


        for (bytePixelArray in bytePixelArray) {
            tmp =
                (tmp or ((bytePixelArray.toInt() shl toShift[shiftIndex % toShift.size] and andByte[shiftIndex++ % toShift.size])).toByte())

            if (shiftIndex % toShift.size == 0) {
                byteEncryptedMessage.addElement(tmp)

                val bstring =
                    byteArrayOf(byteEncryptedMessage.elementAt(byteEncryptedMessage.size - 1))
                val str = String(bstring, Charset.forName("ISO-8859-1"))

                if (messageDecodingStatus.message?.endsWith(END_MESSAGE_COSTANT) == true) {
                    val temp = ByteArray(byteEncryptedMessage.size)

                    for (index in temp.indices)
                        temp[index] = byteEncryptedMessage[index]

                    val stra = String(temp, Charset.forName("ISO-8859-1"))
                    messageDecodingStatus.message = stra.substring(0, stra.length - 1)
                    messageDecodingStatus.setEnded()

                    break
                } else {
                    messageDecodingStatus.message = messageDecodingStatus.message + str
                    if (messageDecodingStatus.message?.length == START_MESSAGE_COSTANT.length && START_MESSAGE_COSTANT != messageDecodingStatus.message) {
                        messageDecodingStatus.message = ""
                        messageDecodingStatus.setEnded()
                        break
                    }
                }
                tmp = 0x00
            }
        }

        messageDecodingStatus.message?.let {
            messageDecodingStatus.message = it.substring(
                START_MESSAGE_COSTANT.length,
                it.length - END_MESSAGE_COSTANT.length
            )
        }
    }

    fun decodeMessage(encodedImages: List<Bitmap>): String? {

        val messageDecodingStatus =
            MessageDecodingStatus()

        for (bit in encodedImages) {
            val pixels = IntArray(bit.width * bit.height)

            bit.getPixels(
                pixels, 0, bit.width, 0, 0, bit.width,
                bit.height
            )

            val b: ByteArray
            b = ImageUtils.convertArray(pixels)
            decodeMessage(b, messageDecodingStatus)

            if (messageDecodingStatus.isEnded)
                break
        }

        return messageDecodingStatus.message
    }

    private class MessageDecodingStatus internal constructor() {

        internal var message: String? = null
        internal var isEnded: Boolean = false
            private set

        init {
            message = ""
            isEnded = false
        }

        internal fun setEnded() {
            this.isEnded = true
        }
    }
}




