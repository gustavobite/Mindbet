package br.com.mindbet.common.helper

class CPFUtil {
    companion object {
        const val CPF_SIZE = 11
        const val CPF_WITH_MASK_SIZE = 14

        fun validateCPF(cpf: String): Boolean {
            val cpfClean = cpf.replace(".", "").replace("-", "")
            val invalidNumber = cpfClean in arrayOf("11111111111", "22222222222", "33333333333", "44444444444", "55555555555", "66666666666", "77777777777", "88888888888", "99999999999", "00000000000")

            //Check if size is eleven
            if (cpfClean.length != CPF_SIZE || invalidNumber)
                return false

            //Check if is number
            try {
                val number = cpfClean.toLong()
            } catch (e: Exception) {
                return false
            }

            //Continue
            val dvCurrent10 = cpfClean.substring(9, 10).toInt()
            val dvCurrent11 = cpfClean.substring(10, 11).toInt()

            //The sum of the nine first digits determines the tenth digit
            val cpfNineFirst = IntArray(9)
            var i = 9
            while (i > 0) {
                cpfNineFirst[i - 1] = cpfClean.substring(i - 1, i).toInt()
                i--
            }

            //Multiple the nine digits for your weights: 10,9..2
            val sumProductNine = IntArray(9)
            var weight = 10
            var position = 0
            while (weight >= 2) {
                sumProductNine[position] = weight * cpfNineFirst[position]
                weight--
                position++
            }

            //Verify the nineth digit
            var dvForTenthDigit = sumProductNine.sum() % 11
            dvForTenthDigit = 11 - dvForTenthDigit //rule for tenth digit

            if (dvForTenthDigit > 9)
                dvForTenthDigit = 0
            if (dvForTenthDigit != dvCurrent10)
                return false

            //Verify tenth digit
            var cpfTenFirst = cpfNineFirst.copyOf(10)
            cpfTenFirst[9] = dvCurrent10

            //Multiple the nine digits for your weights: 10,9..2
            var sumProductTen = IntArray(10)
            var w = 11
            var p = 0
            while (w >= 2) {
                sumProductTen[p] = w * cpfTenFirst[p]
                w--
                p++
            }

            //Verify the nineth digit
            var dvForeleventhDigit = sumProductTen.sum() % 11
            dvForeleventhDigit = 11 - dvForeleventhDigit //rule for tenth digit

            if (dvForeleventhDigit > 9)
                dvForeleventhDigit = 0
            if (dvForeleventhDigit != dvCurrent11)
                return false

            return true
        }
    }
}