package br.com.mindbet.common.base

interface BaseRepository {
    fun <T> save(data:T)
    fun hasData():Boolean
}