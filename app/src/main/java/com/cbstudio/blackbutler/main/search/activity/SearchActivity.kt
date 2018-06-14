package com.cbstudio.blackbutler.main.search.activity

import android.content.Context
import android.content.pm.ApplicationInfo
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.toast
import com.cbstudio.blackbutler.R
import com.cbstudio.blackbutler.constants.LOG_TAG_DEBUG
import com.cbstudio.blackbutler.databinding.ActivitySearchBinding
import com.cbstudio.blackbutler.databinding.ItemSearchResultBinding
import com.cbstudio.blackbutler.main.base.activity.BaseActivity
import com.cbstudio.blackbutler.main.search.vm.SearchResultItemViewModel
import com.cbstudio.blackbutler.main.search.vm.SearchViewModel
import com.cbstudio.blackbutler.manager.IApplicationsInfoManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SearchActivity : BaseActivity<SearchViewModel, ActivitySearchBinding>
(
        SearchViewModel::class
        , R.layout.activity_search
) {

    @Inject
    lateinit var applicationsInfoManager: IApplicationsInfoManager

    private lateinit var recycleView: RecyclerView

    private val searchResultAdapter = SearchResultAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)
        recycleView = rootView.findViewById(R.id.rccv_result)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recycleView.layoutManager = layoutManager
        recycleView.adapter = searchResultAdapter

        viewDataBinding.root.findViewById<EditText>(R.id.ed_search)
                .addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {}

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        viewModel.textChangeSource.onNext(s.toString())
                    }
                })

        viewModel.textChangeSource
                .debounce(200, TimeUnit.MILLISECONDS)
                .switchMap { searchText ->
                    if (searchText.count() == 0) {
                        // no input
                        return@switchMap io.reactivex.Observable.just(ArrayList<ApplicationInfo>())
                    }

                    // calc: match the calc pattern, ex: 1+1, 2*7, 9-4, 8/2
                    if (searchText.matches(Regex("[0-9]+[+\\-*/][0-9]+"))) {
                        val splitText = searchText.split(Regex("[\\+\\-\\*\\/]"))
                        val operation = searchText.replace(Regex("[0-9]"), "")
                        val para1 = splitText[0].toFloat()
                        val para2 = splitText[1].toFloat()
                        when (operation) {
                            "+" -> {
                                return@switchMap Observable.just(para1 + para2)
                            }
                            "-" -> {
                                return@switchMap Observable.just(para1 - para2)
                            }
                            "*" -> {
                                return@switchMap Observable.just(para1 * para2)
                            }
                            "/" -> {
                                return@switchMap Observable.just(para1 / para2)
                            }
                        }
                    }

                    // common operation
                    val result = applicationsInfoManager.appHashMap
                            .filter {
                                it.key.startsWith(searchText, true)
                            }.map { it.value }
                    io.reactivex.Observable.just(result)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result: Any ->
                    if (result is Float) {
                        searchResultAdapter.resultList = arrayListOf(AppInfo("$result", null, ""))

                    } else if (result is List<*>) {
                        val applicationList = result as List<ApplicationInfo>
                        Log.d(LOG_TAG_DEBUG, "count: ${result.size}")

                        searchResultAdapter.resultList = applicationList.map {
                            val name = packageManager.getApplicationLabel(it).toString()
                            val iconDrawable = it.loadIcon(packageManager)
                            AppInfo(name, iconDrawable, it.packageName)
                        }
                    }
                    searchResultAdapter.notifyDataSetChanged()
                }
    }

    //region [inner class]
    inner class SearchResultAdapter(var resultList: List<AppInfo> = ArrayList())
        : RecyclerView.Adapter<ViewHolder>() {

        private val disposableMap: HashMap<Int, Disposable> = HashMap()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val viewDataBinding = DataBindingUtil.inflate<ItemSearchResultBinding>(inflater,
                    R.layout.item_search_result,
                    parent,
                    false)
            viewDataBinding.vm = SearchResultItemViewModel("")
            return ViewHolder(viewDataBinding)
        }

        override fun getItemCount(): Int {
            return resultList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val hashCode = holder.hashCode()
            val result = resultList[position]

            if (disposableMap.containsKey(hashCode)) {
                val disposable = disposableMap[hashCode]
                disposable?.dispose()
                disposableMap.remove(hashCode)
            }
            holder.vm.resultTextField.set(result.name)
            holder.vm.iconDrawableField.set(result.icon)

            val disposable = holder.vm.onClickSource
                    .subscribe {
                        val launchIntent = packageManager.getLaunchIntentForPackage(result.packageName)
                        if (launchIntent == null) {
                            toast(getString(R.string.unable_open_no_ui_app)).show()
                            return@subscribe
                        }
                        startActivity(launchIntent)
                        finish()
                    }

            disposableMap[hashCode] = disposable
        }
    }
    // endregion


    // region [data class]
    data class AppInfo(val name: String, val icon: Drawable?, val packageName: String)
    // endregion


    // region [model class]
    class ViewHolder(val viewDataBinding: ItemSearchResultBinding,
                     val vm: SearchResultItemViewModel = viewDataBinding.vm as SearchResultItemViewModel,
                     val tv: TextView = viewDataBinding.root.findViewById(R.id.tv_result)) :
            RecyclerView.ViewHolder(viewDataBinding.root)
    // endregion

}

