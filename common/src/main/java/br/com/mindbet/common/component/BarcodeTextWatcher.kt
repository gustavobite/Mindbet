package br.com.mindbet.common.component

import android.text.Editable
import android.text.TextWatcher
import br.com.mindbet.common.extension.*
import com.google.android.material.textfield.TextInputEditText
import java.lang.StringBuilder

class BarcodeTextWatcher(val editText: TextInputEditText, private val breakLine: Boolean = false, val onTextChangedListener: (() -> Unit)? = null): TextWatcher {

    private var isUpdating: Boolean = false
    private var old = ""
    lateinit var currentMask: String
    var differenceCursorFromLastPosition = 0

    override fun afterTextChanged(p0: Editable?) {}

    override fun beforeTextChanged(oldText: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (!isUpdating && oldText != null) {
            currentMask = selectMask(oldText.toString().unMaskText() ?: "")
            differenceCursorFromLastPosition = oldText.length - editText.selectionEnd
        }
    }

    private fun unmask(s: String?) = s?.replace("[.]".toRegex(), "")?.replace("[-]".toRegex(), "")
        ?.replace("[/]".toRegex(), "")?.replace("[(]".toRegex(), "")
        ?.replace("[)]".toRegex(), "")?.replace("[ ]".toRegex(), "")?.replace("[\n]".toRegex(), "")


    private fun mask(format: String?, text: String?): String {
        var maskedText = ""
        if (!text.isNullOrEmpty() && !format.isNullOrEmpty()) {
            var i = 0
            for (m in format.toCharArray()) {
                if (m != '#') {
                    maskedText += m
                    continue
                }
                try {
                    if (breakLine && getBreakLineIndexMask(text) == i) {
                        maskedText += "\n"
                    }
                    maskedText += text[i]
                } catch (e: Exception) {
                    break
                }

                i++
                if (i >= text.length) break
            }
        }
        return maskedText
    }

    override fun onTextChanged(barcode: CharSequence?, start: Int, count: Int, after: Int) {
        if (!isUpdating) {
            val newMask = selectMask(barcode.toString().unMaskText() ?: "")
            val str = unmask(barcode.toString())
            val maskedStr: String

            maskedStr = mask(newMask, str)

            isUpdating = true

            var selectionPos = start
            if (after > 0 && selectionPos < newMask.length) {
                while (newMask[selectionPos] != '#') {
                    selectionPos++
                }
                selectionPos += after
            }

            editText.setText(maskedStr)
            old = maskedStr
            isUpdating = false

            if(currentMask != newMask && after == 0){
                selectionPos = maskedStr.length - differenceCursorFromLastPosition
            }


            if (selectionPos < 0) selectionPos = 0
            if (selectionPos > maskedStr.length) {
                selectionPos = maskedStr.length
            }
            if (selectionPos > newMask.length) {
                selectionPos = maskedStr.length
            }
            if (maskedStr.length > newMask.length) {
                selectionPos = maskedStr.length
            }
            val differ = maskedStr.length - start
            if (differ in 2..3) {
                selectionPos = maskedStr.length
            }

            try {
                editText.setSelection(selectionPos)
                onTextChangedListener?.invoke()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun selectMask(fullBarCode: String): String {
        return when {
            fullBarCode.length <= 47 -> BARCODE_MASK_47
            else -> BARCODE_MASK_48
        }
    }

    private fun getBreakLineIndexMask(fullBarCode: String): Int {
        return when {
            fullBarCode.length > 47 -> 12
            else -> 21
        }
    }
}