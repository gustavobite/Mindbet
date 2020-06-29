package br.com.mindbet.common.helper

import android.content.Context
import android.os.Build
import android.provider.Settings

class DeviceInformations {

    companion object{
        val model: String = Build.MODEL
        val versionNameOS: String = Build.VERSION.CODENAME
        fun getDeviceUUID(context: Context): String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}