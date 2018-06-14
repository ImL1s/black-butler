package com.cbstudio.blackbutler.main.base.activity

import android.annotation.TargetApi
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import androidx.core.widget.toast
import com.cbstudio.blackbutler.BR
import com.cbstudio.blackbutler.BlackButlerApplication
import com.cbstudio.blackbutler.R
import com.cbstudio.blackbutler.constants.ACTIVITY_REQUEST_CODE_OVERLAYS
import com.cbstudio.blackbutler.constants.LOG_TAG_DEBUG
import com.cbstudio.blackbutler.dagger.component.ActivityComponent
import com.cbstudio.blackbutler.dagger.component.AppComponent
import com.cbstudio.blackbutler.dagger.component.DaggerActivityComponent
import com.cbstudio.blackbutler.extensions.startActivityForResult
import com.cbstudio.blackbutler.main.base.vm.BaseViewModel
import kotlin.reflect.KClass


/**
 * Created by ImL1s on 2018/6/6.
 * Description:
 */
abstract class BaseActivity<TViewModel : BaseViewModel, TViewDataBinding : ViewDataBinding>(
        private val viewModelClazz: KClass<TViewModel>,
        @LayoutRes private val layout: Int
) : AppCompatActivity() {

    protected lateinit var viewDataBinding: TViewDataBinding
        private set

    protected lateinit var viewModel: TViewModel
        private set

    protected val isVersionGreaterThanM
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    protected val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
                .build()
    }

    protected val appComponent: AppComponent
        get() = application.appComponent

    protected val application
        get() = applicationContext as BlackButlerApplication

    val rootView: View by lazy { return@lazy viewDataBinding.root }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initViewDataBinding()
        if (isVersionGreaterThanM) {
            checkDrawOverlaysPermission()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
        // add another process
        }

        when {
            (requestCode == ACTIVITY_REQUEST_CODE_OVERLAYS && isVersionGreaterThanM) -> {
                handleOverlaysResult()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initViewDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layout)
        viewDataBinding.setVariable(BR.vm, viewModel)
        viewDataBinding.setLifecycleOwner(this)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(viewModelClazz.java)
        viewModel.toastLiveData.observe(this, Observer { toastContent ->
            toastContent?.let { toast(it).show() }
        })
        viewModel.logLiveData.observe(this, Observer { logContent ->
            logContent?.let { Log.d(LOG_TAG_DEBUG, logContent) }
        })
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun checkDrawOverlaysPermission() {
        if (!Settings.canDrawOverlays(this)) {
            Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName"))
                    .startActivityForResult(this, ACTIVITY_REQUEST_CODE_OVERLAYS)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun handleOverlaysResult() {
        if (!Settings.canDrawOverlays(this)) {
            toast(R.string.not_granted_overlays).show()
        }
    }
}