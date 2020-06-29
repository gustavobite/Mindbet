package br.com.mindbet.common.dependency

import br.com.mindbet.common.steganography.interactor.SteganographyDecode
import br.com.mindbet.common.steganography.interactor.SteganographyDecodeImpl
import br.com.mindbet.common.steganography.interactor.SteganographyEncode
import br.com.mindbet.common.steganography.interactor.SteganographyEncodeImpl
import br.com.mindbet.common.steganography.utils.DecodeHelper
import br.com.mindbet.common.steganography.utils.EncodeHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object SteganographyModule {
    val dependencyModule = module {
        single { DecodeHelper() }
        single { EncodeHelper() }
        factory<SteganographyDecode> { SteganographyDecodeImpl(decodeHelper = get()) }
        factory<SteganographyEncode> { SteganographyEncodeImpl(context = androidContext(),encodeHelper = get()) }
    }

}