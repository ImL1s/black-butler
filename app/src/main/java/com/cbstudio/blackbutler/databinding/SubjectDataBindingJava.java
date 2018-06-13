package com.cbstudio.blackbutler.databinding;

import android.databinding.BindingConversion;
import android.view.View;

import io.reactivex.subjects.Subject;
import kotlin.Unit;

/**
 * Created by ImL1s on 2018/6/13.
 * Description:
 */
public class SubjectDataBindingJava {

    @BindingConversion
    public static View.OnClickListener subjectUnitToOnClickListener(final Subject<kotlin.Unit> subject) {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subject.onNext(Unit.INSTANCE);
            }
        };
    }
}
