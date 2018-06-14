package com.cbstudio.blackbutler.manager

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager


interface IApplicationsInfoManager {
    val appHashMap: HashMap<String, ApplicationInfo>
}

/**
 * Created by ImL1s on 2018/6/14.
 * Description:
 */
class ApplicationsInfoManager(val context: Context) : IApplicationsInfoManager {

    private val packageManager = context.packageManager
    override val appHashMap = HashMap<String, ApplicationInfo>()


    init {
        initAppList()
    }

    private fun initAppList() {
        val pm = packageManager
        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        packages?.let {
            it.forEach {
                val appLabelName = packageManager.getApplicationLabel(it).toString()
                appHashMap[appLabelName] = it
            }
        }
    }
}