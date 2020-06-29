package br.com.mindbet.common.helper

import android.graphics.Bitmap
import java.util.*


object ImageUtils {

    private val SQUARE_BLOCK_SIZE = 512
    private val TAG = ImageUtils::class.java.name

    fun splitImage(bitmap: Bitmap): List<Bitmap> {

        var chunkHeight: Int
        var chunkWidth: Int

        val chunkedImages = ArrayList<Bitmap>()

        var rows = bitmap.height / SQUARE_BLOCK_SIZE
        var cols = bitmap.width / SQUARE_BLOCK_SIZE

        val chunk_height_mod = bitmap.height % SQUARE_BLOCK_SIZE
        val chunk_width_mod = bitmap.width % SQUARE_BLOCK_SIZE

        if (chunk_height_mod > 0)
            rows++
        if (chunk_width_mod > 0)
            cols++


        var y_coordinate = 0

        for (x in 0 until rows) {

            var x_coordinate = 0

            for (y in 0 until cols) {

                chunkHeight = SQUARE_BLOCK_SIZE
                chunkWidth = SQUARE_BLOCK_SIZE

                if (y == cols - 1 && chunk_width_mod > 0)
                    chunkWidth = chunk_width_mod

                if (x == rows - 1 && chunk_height_mod > 0)
                    chunkHeight = chunk_height_mod

                //Adding chunk images to the list
                chunkedImages.add(
                    Bitmap.createBitmap(
                        bitmap,
                        x_coordinate,
                        y_coordinate,
                        chunkWidth,
                        chunkHeight
                    )
                )
                x_coordinate += SQUARE_BLOCK_SIZE

            }

            y_coordinate += SQUARE_BLOCK_SIZE

        }

        return chunkedImages
    }



    fun convertArray(array: IntArray): ByteArray {

        val newarray = ByteArray(array.size * 3)

        for (i in array.indices) {

            newarray[i * 3] = (array[i] shr 16 and 0xFF).toByte()
            newarray[i * 3 + 1] = (array[i] shr 8 and 0xFF).toByte()
            newarray[i * 3 + 2] = (array[i] and 0xFF).toByte()

        }

        return newarray
    }

}
