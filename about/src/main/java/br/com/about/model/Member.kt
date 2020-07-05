package br.com.about.model

import br.com.mindbet.common.extension.createUUID

class Member(
    val name: String? = null,
    val job: String? = null,
    val image:String? = null,
    val id:String? = createUUID(false)
)