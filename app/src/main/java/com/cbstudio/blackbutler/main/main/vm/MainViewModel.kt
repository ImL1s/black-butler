package com.cbstudio.blackbutler.main.main.vm

import com.cbstudio.blackbutler.main.base.vm.BaseViewModel
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject


/**
 * Created by ImL1s on 2018/6/5.
 * Description:
 */
class MainViewModel : BaseViewModel() {

    val startClickSubject: Subject<Unit> = BehaviorSubject.create()
    val stopClickSubject: Subject<Unit> = BehaviorSubject.create()

}