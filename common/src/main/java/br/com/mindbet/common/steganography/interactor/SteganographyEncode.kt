package br.com.mindbet.common.steganography.interactor


import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.interactor.UseCase
import br.com.mindbet.common.steganography.model.Steganography

abstract class SteganographyEncode : UseCase<String, Resource<Steganography>>()