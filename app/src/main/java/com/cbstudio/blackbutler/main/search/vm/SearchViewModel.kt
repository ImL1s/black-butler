package com.cbstudio.blackbutler.main.search.vm

import android.arch.lifecycle.MutableLiveData
import com.cbstudio.blackbutler.main.base.vm.BaseViewModel
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit


/**
 * Created by ImL1s on 2018/6/8.
 * Description:
 */
class SearchViewModel : BaseViewModel() {

    val searchTextLiveData = MutableLiveData<String>()
    val textChangeSource = BehaviorSubject.create<String>()

    init {
        var count = 0
        textChangeSource
                .debounce(400, TimeUnit.MILLISECONDS)
                .doOnNext { logLiveData.postValue("count: ${count++}") }
                .subscribe {
                    // TODO search
                }

        searchTextLiveData.observeForever { it?.let { textChangeSource.onNext(it) } }
    }
}