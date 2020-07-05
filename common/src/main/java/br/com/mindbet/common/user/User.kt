package br.com.mindbet.common.user

data class User(var uid: String,
                var identificationNumber: String? = null,
                var name: String? = null,
                var email: String? = null,
                var password: String? = null)