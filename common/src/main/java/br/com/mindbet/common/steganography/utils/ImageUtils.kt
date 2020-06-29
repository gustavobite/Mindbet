package br.com.mindbet.common.steganography.utils


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import java.util.*


object ImageUtils {
    private val SQUARE_BLOCK_SIZE = 512
    private val TAG = ImageUtils::class.java.name


    /**
     * This method splits the image into many images of ( SQUARE_BLOCK_SIZE * SQUARE_BLOCK_SIZE ) size.
     *
     * @return : List of splitted images {List}
     * @parameter : Image {Bitmap}
     */
    fun splitImage(bitmap: Bitmap): List<Bitmap> {

        var chunkHeight: Int
        var chunkWidth: Int

        val chinkedImages = ArrayList<Bitmap>()


        var rows = bitmap.height / SQUARE_BLOCK_SIZE
        var cols = bitmap.width / SQUARE_BLOCK_SIZE

        val chunkHeightMod = bitmap.height % SQUARE_BLOCK_SIZE
        val chunkWidthMod = bitmap.width % SQUARE_BLOCK_SIZE

        if (chunkHeightMod > 0)
            rows++
        if (chunkWidthMod > 0)
            cols++

        var yCoordinate = 0

        for (x in 0 until rows) {

            var xCoordinate = 0

            for (y in 0 until cols) {

                chunkHeight = SQUARE_BLOCK_SIZE
                chunkWidth = SQUARE_BLOCK_SIZE

                if (y == cols - 1 && chunkWidthMod > 0)
                    chunkWidth = chunkWidthMod

                if (x == rows - 1 && chunkHeightMod > 0)
                    chunkHeight = chunkHeightMod

                //Adding chunk images to the list
                chinkedImages.add(
                    Bitmap.createBitmap(
                        bitmap,
                        xCoordinate,
                        yCoordinate,
                        chunkWidth,
                        chunkHeight
                    )
                )
                xCoordinate += SQUARE_BLOCK_SIZE

            }

            yCoordinate += SQUARE_BLOCK_SIZE

        }

        return chinkedImages
    }

    /**
     * This method merge all the chunk image list into one single image
     *
     * @return : Merged Image {Bitmap}
     * @parameter : List {Bitmap}, Original Height {Integer}, Original Width {Integer}
     */
    fun mergeImage(images: List<Bitmap>, original_height: Int, original_width: Int): Bitmap {

        //Calculating number of Rows and columns of that matrix
        var rows = original_height / SQUARE_BLOCK_SIZE
        var cols = original_width / SQUARE_BLOCK_SIZE

        val chunkHeightMod = original_height % SQUARE_BLOCK_SIZE
        val chunkWidthMod = original_width % SQUARE_BLOCK_SIZE

        if (chunkHeightMod > 0)
            rows++
        if (chunkWidthMod > 0)
            cols++

        Log.d(TAG, "Size width $original_width size height $original_height")
        val bitmap = Bitmap.createBitmap(original_width, original_height, Bitmap.Config.ARGB_4444)

        val canvas = Canvas(bitmap)

        var count = 0

        for (irows in 0 until rows) {
            for (icols in 0 until cols) {

                canvas.drawBitmap(
                    images[count],
                    (SQUARE_BLOCK_SIZE * icols).toFloat(),
                    (SQUARE_BLOCK_SIZE * irows).toFloat(),
                    Paint()
                )
                count++

            }
        }

        return bitmap
    }

    /**
     * This method converts the byte array to an integer array.
     *
     * @return : Integer Array
     * @parameter : b {the byte array}
     */

    fun byteArrayToIntArray(b: ByteArray): IntArray {

        Log.v("Size byte array", b.size.toString() + "")

        val size = b.size / 3

        Log.v("Size Int array", size.toString() + "")

        System.runFinalization()
        System.gc()

        Log.v("FreeMemory", Runtime.getRuntime().freeMemory().toString() + "")
        val result = IntArray(size)
        var offset = 0
        var index = 0

        while (offset < b.size) {
            result[index++] = byteArrayToInt(b, offset)
            offset += 3
        }

        return result
    }

    /**
     * Convert the byte array to an int starting from the given offset.
     *
     * @return :  Integer
     * @parameter :  b {the byte array}, offset {integer}
     */
    private fun byteArrayToInt(b: ByteArray, offset: Int): Int {

        var value = 0x00000000

        for (i in 0..2) {
            val shift = (3 - 1 - i) * 8
            value = value or (b[i + offset] and 0x000000FF shl shift)
        }

        value = value and 0x00FFFFFF

        return value
    }

    /**
     * Convert integer array representing [argb] values to byte array
     * representing [rgb] values
     *
     * @return : byte Array representing [rgb] values.
     * @parameter : Integer array representing [argb] values.
     */
    fun convertArray(array: IntArray): ByteArray {

        val newarray = ByteArray(array.size * 3)

        for (i in array.indices) {

            newarray[i * 3] = (array[i] shr 16 and 0xFF).toByte()
            newarray[i * 3 + 1] = (array[i] shr 8 and 0xFF).toByte()
            newarray[i * 3 + 2] = (array[i] and 0xFF).toByte()

        }

        return newarray
    }

    /**
     * This method is used to check whether the string is empty of not
     *
     * @return : true or false {boolean}
     * @parameter : String
     */
    fun isStringEmpty(str: String?): Boolean {
        var result = true

        str?.let {
            val newstr = str.trim { it <= ' ' }
            if (newstr.isNotEmpty() && newstr != "undefined")
                result = false
        }

        return result
    }

}

infix fun Byte.and(that: Int): Int = this.toInt().and(that)
infix fun Int.and(that: Byte): Int = this.and(that.toInt())
