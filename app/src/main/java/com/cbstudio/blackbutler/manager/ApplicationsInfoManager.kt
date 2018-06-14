package com.cbstudio.blackbutler.manager

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import com.cbstudio.blackbutler.constants.LOG_TAG_ERROR
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by ImL1s on 2018/6/14.
 * Description:
 */
interface IApplicationsInfoManager {
    val appHashMap: HashMap<String, ApplicationInfo>
}

class ApplicationsInfoManager(val context: Context) : IApplicationsInfoManager {

    private val packageManager = context.packageManager
    override val appHashMap = HashMap<String, ApplicationInfo>()


    init {
        initAppList()
    }

    private fun initAppList() {
        Observable.just(packageManager)
                .map { it.getInstalledApplications(PackageManager.GET_META_DATA) }
                .observeOn(Schedulers.io())
                .subscribe({
                    it.forEach {
                        val appLabelName = packageManager.getApplicationLabel(it).toString()
                        appHashMap[appLabelName] = it
                    }
                }, { Log.d(LOG_TAG_ERROR, it.stackTrace.toString()) })
    }
}