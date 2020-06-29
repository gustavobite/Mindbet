package br.com.mindbet.common.helper

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


/**
 * Created by Luan Gabriel on 17/09/19.
 */

class RequestPermissionProvider(val activity: Activity) {
    companion object {
        const val REQUEST_CODE_RUNTIME_PERMISSION = 0x1
        const val REQUEST_CODE_LOCATION_SETTINGS = 0x2
    }

    private var requestPermissionListener: RequestPermissionListener? = null

    fun requestPermission(permissions: Array<String>, listener: RequestPermissionListener?) {
        requestPermissionListener = listener

        if (!needRequestRuntimePermissions()) {
            requestPermissionListener?.onSuccess()
            return
        }
        requestUnGrantedPermissions(permissions, REQUEST_CODE_RUNTIME_PERMISSION)
    }

    private fun needRequestRuntimePermissions(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    private fun requestUnGrantedPermissions(permissions: Array<String>, requestCode: Int) {
        val unGrantedPermissions = findUnGrantedPermissions(permissions)
        if (unGrantedPermissions.isEmpty()) {
            requestPermissionListener?.onSuccess()
            return
        }
        ActivityCompat.requestPermissions(activity, unGrantedPermissions, requestCode)
    }

    private fun isPermissionGranted(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun findUnGrantedPermissions(permissions: Array<String>): Array<String> {
        val unGrantedPermissionList = ArrayList<String>()
        for (permission in permissions) {
            if (!isPermissionGranted(permission)) {
                unGrantedPermissionList.add(permission)
            }
        }
        return unGrantedPermissionList.toTypedArray()
    }

    fun onRequestPermissionsResult(requestCode: Int,
                                   grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_RUNTIME_PERMISSION) {
            if (!grantResults.isEmpty()) {
                for (grantResult in grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        requestPermissionListener?.onFailed()
                        return
                    }
                }
                requestPermissionListener?.onSuccess()
            } else {
                requestPermissionListener?.onFailed()
            }
        }
    }

    //    <--------------  Fragment  --------------------->
    fun requestLocationPermissionFragment(fragment: Fragment, listener: RequestPermissionListener?, askPermissionAnyway: Boolean = false) {
        val permissions: Array<String> = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (!askPermissionAnyway) {
            return
        }
        return requestPermissionFragment(fragment, permissions, object : RequestPermissionListener {

            override fun onSuccess() {
                listener?.onSuccess()
            }

            override fun onFailed() {
                listener?.onFailed()
            }
        })
    }

    fun requestPermissionFragment(fragment: Fragment, permissions: Array<String>, listener: RequestPermissionListener?) {
        requestPermissionListener = listener

        if (!needRequestRuntimePermissions()) {
            requestPermissionListener?.onSuccess()
            return
        }
        requestUnGrantedPermissionsFragments(fragment, permissions, REQUEST_CODE_RUNTIME_PERMISSION)
    }

    private fun requestUnGrantedPermissionsFragments(fragment: Fragment, permissions: Array<String>, requestCode: Int) {
        val unGrantedPermissions = findUnGrantedPermissionsFragments(permissions)
        if (unGrantedPermissions.isEmpty()) {
            requestPermissionListener?.onSuccess()
            return
        }
        fragment.requestPermissions(unGrantedPermissions, requestCode)
    }

    private fun isPermissionGrantedFragments(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun findUnGrantedPermissionsFragments(permissions: Array<String>): Array<String> {
        val unGrantedPermissionList = ArrayList<String>()
        for (permission in permissions) {
            if (!isPermissionGrantedFragments(permission)) {
                unGrantedPermissionList.add(permission)
            }
        }
        return unGrantedPermissionList.toTypedArray()
    }

    interface RequestPermissionListener {
        fun onSuccess()

        fun onFailed()
    }
}