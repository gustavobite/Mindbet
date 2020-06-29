package br.com.mindbet.common.base

data class ResultData<T>(val data: T)
data class ResultDataList<T>(val data: MutableList<T>)