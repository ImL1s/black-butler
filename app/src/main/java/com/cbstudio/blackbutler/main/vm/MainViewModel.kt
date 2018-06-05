package com.cbstudio.blackbutler.main.vm

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel


/**
 * Created by ImL1s on 2018/6/5.
 * Description:
 */
class MainViewModel(val lifeCycle: Lifecycle) : ViewModel() {


    val a: LiveData<String> = MutableLiveData<String>()

}