package com.cbstudio.blackbutler.main.search.activity

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.cbstudio.blackbutler.R
import com.cbstudio.blackbutler.databinding.ActivitySearchBinding
import com.cbstudio.blackbutler.main.base.activity.BaseActivity
import com.cbstudio.blackbutler.main.search.vm.SearchViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class SearchActivity : BaseActivity<SearchViewModel, ActivitySearchBinding>
(
        SearchViewModel::class
        , R.layout.activity_search
) {

    lateinit var appList: List<ApplicationInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.root.findViewById<EditText>(R.id.ed_search)
                .addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {

                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        viewModel.textChangeSource.onNext(s.toString())
                    }

                })

        viewModel.textChangeSource
                .debounce(400, TimeUnit.MILLISECONDS)
                .map { searchText ->
                    appList
                            .map { packageManager.getApplicationLabel(it).toString() }
                            .filter { appLabelName -> appLabelName.startsWith(searchText) }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    rootView.findViewById<Spinner>(R.id.sp_result).adapter = ArrayAdapter<String>(
                            this,
                            android.R.layout.simple_spinner_dropdown_item
                            , result
                    )
                }

        initAppList()
    }

    private fun initAppList() {
        val pm = packageManager
        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        packages?.let { appList = it }
//        packages.forEach {
//            val launchIntent = pm.getLaunchIntentForPackage(it.packageName)
//            startActivity(launchIntent)
//        }

    }
}
