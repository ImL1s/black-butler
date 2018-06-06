package com.cbstudio.blackbutler.main.main.activity

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.cbstudio.blackbutler.R
import com.cbstudio.blackbutler.databinding.ActivityMainBinding
import com.cbstudio.blackbutler.extensions.startService
import com.cbstudio.blackbutler.main.base.activity.BaseActivity
import com.cbstudio.blackbutler.main.main.vm.MainViewModel
import com.cbstudio.blackbutler.services.FloatViewService

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>
(MainViewModel::class, R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        viewModel.startClickSubject
                .subscribe {
                    Intent(this, FloatViewService::class.java)
                            .startService(this)
                }

        viewModel.stopClickSubject
                .subscribe {
                    // TODO stop service
                }
    }
}
