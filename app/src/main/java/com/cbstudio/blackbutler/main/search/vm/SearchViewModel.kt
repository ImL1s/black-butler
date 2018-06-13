package com.cbstudio.blackbutler.main.search.vm

import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.content.pm.ResolveInfo
import com.cbstudio.blackbutler.main.base.vm.BaseViewModel
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit


/**
 * Created by ImL1s on 2018/6/8.
 * Description:
 */
class SearchViewModel : BaseViewModel() {

    val searchTextLiveData = MutableLiveData<String>()
    val searchResultLiveData = MutableLiveData<List<String>>()
    val textChangeSource = BehaviorSubject.create<String>()

    init {
        textChangeSource
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribe {
                    // TODO search
                }

        searchTextLiveData.observeForever { it?.let { textChangeSource.onNext(it) } }
    }


}