package com.cbstudio.blackbutler.main.search.vm

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.graphics.drawable.Drawable
import io.reactivex.subjects.BehaviorSubject


/**
 * Created by ImL1s on 2018/6/13.
 * Description:
 */
class SearchResultItemViewModel(text: String) : ViewModel() {

    val resultTextField = ObservableField<String>(text)
    val iconDrawableField = ObservableField<Drawable>()
    val onClickSource = BehaviorSubject.create<Unit>()

}