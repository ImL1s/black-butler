package com.cbstudio.blackbutler.main.main.vm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.cbstudio.blackbutler.extensions.onNext
import com.cbstudio.blackbutler.main.base.vm.BaseViewModel
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject


/**
 * Created by ImL1s on 2018/6/5.
 * Description:
 */
class MainViewModel : BaseViewModel() {

    val textLiveData: MutableLiveData<String> = MutableLiveData()
    val clickSubject: Subject<Unit> = BehaviorSubject.create()

    init {
        textLiveData.value = "CLICK"
    }

    override fun onCleared() {
        super.onCleared()
    }
}