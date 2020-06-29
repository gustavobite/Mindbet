package br.com.mindbet.common.helper

class CNPJUtil {
    companion object {

        fun myValidateCNPJ(cnpj: String, firstDigit: Boolean): Boolean {
            val cnpjClean = cnpj
                    .replace(".", "")
                    .replace("-", "")
                    .replace("/", "")

            val invalidNumber = cnpjClean in arrayOf("00000000000000","11111111111111",
                    "22222222222222", "33333333333333", "44444444444444", "55555555555555",
                    "66666666666666", "77777777777777", "88888888888888", "99999999999999")

            if (cnpjClean.length != 14 || invalidNumber)
                return false

            //Check if is number
            try {
                val number = cnpjClean.toLong()
            } catch (e: Exception) {
                return false
            }

            val startPos = when (firstDigit) {
                true -> 11
                else -> 12
            }

            val weightOffset = when (firstDigit) {
                true -> 0
                false -> 1
            }

            val sum = (startPos downTo 0).fold(0) { acc, pos ->
                val weight = 2 + ((11 + weightOffset - pos) % 8)
                val num = cnpjClean[pos].toString().toInt()
                val sum = acc + (num * weight)
                sum
            }

            val result = sum % 11
            val expectedDigit = when (result) {
                0, 1 -> 0
                else -> 11 - result
            }

            val actualDigit = cnpjClean[startPos + 1].toString().toInt()

            return expectedDigit == actualDigit
        }
    }
}