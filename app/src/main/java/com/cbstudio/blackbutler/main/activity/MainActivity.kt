package com.cbstudio.blackbutler.main.activity

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cbstudio.blackbutler.R
import com.cbstudio.blackbutler.main.vm.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewDataBinding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_main)

        val vm = MainViewModel(lifecycle)

        viewDataBinding.setLifecycleOwner(this)

    }

//
}
