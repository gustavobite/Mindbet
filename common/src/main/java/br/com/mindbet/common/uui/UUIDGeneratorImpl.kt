package br.com.mindbet.common.uui

import br.com.mindbet.common.extension.createUUID

object UUIDGeneratorImpl : UUIDGenerator {
    override fun getUUID(): String {
        return createUUID(false)
    }
}