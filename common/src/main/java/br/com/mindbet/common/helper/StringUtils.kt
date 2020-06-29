package br.com.mindbet.common.helper

object StringUtils {
	@JvmStatic
	fun getDigit(value : String?, index: Int) : String {
		if (index < value?.length ?: 0) {
			return value?.get(index).toString()
		}
		return ""
	}
}