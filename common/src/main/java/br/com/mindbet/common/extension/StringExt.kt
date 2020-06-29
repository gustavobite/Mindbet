package br.com.mindbet.common.extension


fun String?.getOnlyDigits(): String{
    return this?.replace("[^0-9]".toRegex(), "") ?: ""
}

fun String?.checkEqualsNull(): String? {
    return if (this?.isNotNullOrEmpty() == true && !this.equals("null", true)) return this else null
}

fun String?.removeMilhar(): String? {
    return this?.replace(".", "")?.replace(",", ".")
}
