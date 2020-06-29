package br.com.mindbet.common.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class MaskTextWatcher(var editText: EditText?, var currentMask: String) : TextWatcher {
    private var isUpdating: Boolean = false
    private var old = ""

    private fun unmask(s: String?) = s?.replace("[.]".toRegex(), "")?.replace("[-]".toRegex(), "")
        ?.replace("[/]".toRegex(), "")?.replace("[(]".toRegex(), "")
        ?.replace("[)]".toRegex(), "")?.replace("[ ]".toRegex(), "")


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


    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (!isUpdating) {
            val str = unmask(s.toString())
            val maskedStr: String

            maskedStr = mask(currentMask, str)

            isUpdating = true

            var selectionPos = start
            if (count > 0 && selectionPos < currentMask.length) {
                while (currentMask[selectionPos] != '#') {
                    selectionPos++
                }
                selectionPos += count
            }

            editText?.setText(maskedStr)
            old = maskedStr
            isUpdating = false

            if (selectionPos < 0) selectionPos = 0
            if (selectionPos > maskedStr.length) {
                selectionPos = maskedStr.length
            }
            if (selectionPos > currentMask.length) {
                selectionPos = currentMask.length
            }
            editText?.setSelection(selectionPos)
        }
    }

    override fun afterTextChanged(p0: Editable?) {}

    override fun equals(other: Any?) =
        other is MaskTextWatcher && other.editText?.equals(this.editText) == true
                && other.currentMask == this.currentMask

    override fun hashCode(): Int {
        var result = editText?.hashCode() ?: 0
        result = 31 * result + currentMask.hashCode()
        result = 31 * result + isUpdating.hashCode()
        result = 31 * result + old.hashCode()
        return result
    }
}