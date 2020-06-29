package br.com.mindbet.common.helper

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

object PermissionUtils{

    fun askForPermissions(permissions: List<String>, activity: FragmentActivity?, requestCode:Int) : Boolean{

        val permissionList = ArrayList<String>()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions){
                val hasPermission = ContextCompat.checkSelfPermission(activity as Activity,permission) == PackageManager.PERMISSION_GRANTED
                if(!hasPermission) permissionList.add(permission)
            }

            if(permissionList.isEmpty()) return true
            else{
                ActivityCompat.requestPermissions(activity as Activity,permissionList.toTypedArray(),requestCode)
            }
        }
        return true
    }
}