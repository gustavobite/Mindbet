package br.com.mindbet.application

import android.app.Application
import br.com.mindbet.common.dependency.GetUUIDModule
import br.com.mindbet.common.dependency.NetworkModule
import br.com.mindbet.common.dependency.SteganographyModule
import br.com.mindbet.core.dependencies.CoreDependencies
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MindbetApp : Application() {


    companion object {
        lateinit var instance: MindbetApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MindbetApp)
            modules(listOf(
                NetworkModule.dependencyModule,
                SteganographyModule.dependencyModule,
                GetUUIDModule.dependencyModule,
                CoreDependencies.dependencies
            ))
        }

    }
}