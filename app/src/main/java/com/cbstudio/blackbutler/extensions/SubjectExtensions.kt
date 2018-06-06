package com.cbstudio.blackbutler.extensions

import io.reactivex.subjects.Subject


/**
 * Created by ImL1s on 2018/6/6.
 * Description:
 */

fun Subject<Unit>.onNext() {
    this.onNext(Unit)
}