package com.cbstudio.blackbutler.databinding

import android.databinding.BindingConversion
import android.view.View
import com.cbstudio.blackbutler.extensions.onNext
import io.reactivex.subjects.Subject


/**
 * Created by ImL1s on 2018/6/6.
 * Description:
 */

object SubjectDataBinding {

    @JvmStatic
    @BindingConversion
    fun subjectUnitToOnClickListener(subject: Subject<Unit>?): View.OnClickListener? {
        if (subject != null) {
            return View.OnClickListener { subject.onNext() }
        }
        return null
    }
}