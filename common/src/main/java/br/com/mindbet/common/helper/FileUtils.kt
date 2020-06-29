package br.com.mindbet.common.helper

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.util.*

object FileUtils {

    fun getUriFile(context: Context, file: File): Uri {
        return context.let {
            FileProvider.getUriForFile(
                it, it.applicationContext
                    .packageName.toString() + ".provider", file
            )
        }
    }

    fun getParentFile (parentFile: ParentFile): File {
        val filePath = Environment.getExternalStorageDirectory().path + "/${parentFile.name.toLowerCase(Locale.getDefault())}"
        val rootFile = File(filePath)
        if (!rootFile.exists())
            rootFile.mkdir()
        return rootFile
    }

    enum class ParentFile(name: String) {
        DOCUMENTS("documents")
    }
}