package com.cbstudio.blackbutler.main.base.vm

import android.arch.lifecycle.ViewModel
import com.cbstudio.blackbutler.livedata.SingleLiveEvent


/**
 * Created by ImL1s on 2018/6/6.
 * Description:
 */
abstract class BaseViewModel : ViewModel() {

    val toastLiveData: SingleLiveEvent<String> = SingleLiveEvent()
    val logLiveData: SingleLiveEvent<String> = SingleLiveEvent()

    override fun onCleared() {
        super.onCleared()
        // release any memory use
    }

}