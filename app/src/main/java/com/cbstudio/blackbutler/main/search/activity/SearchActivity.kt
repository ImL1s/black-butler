package com.cbstudio.blackbutler.main.search.activity

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.net.Uri
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
import com.cbstudio.blackbutler.typedefine.ResultClick
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.net.URLEncoder
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
                        return@switchMap calc(searchText)
                    }

                    if (searchText.count() > 2 && searchText.startsWith("g ", true)) {
                        return@switchMap Observable.just(searchText.substring(1).trim())
                    }

                    // common operation
                    searchApp(searchText)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result: Any ->
                    when (result) {
                        is Float -> {
                            searchResultAdapter.resultList = arrayListOf(AppInfo(this, "$result", null, ""))
                        }
                        is List<*> -> {
                            refreshAdapterData(result as List<ApplicationInfo>)
                        }
                        is String -> {
                            val uri = makeSearchWithGoogleUri(result)
                            searchResultAdapter.resultList = arrayListOf(
                                    SearchInfo(this, result, resources.getDrawable(R.drawable.ic_google, theme), uri)
                            )
                        }
                    }
                    searchResultAdapter.notifyDataSetChanged()
                }
    }

    private fun makeSearchWithGoogleUri(searchText: String): Uri {
        val query = URLEncoder.encode(searchText, "UTF-8")
        return Uri.parse("https://www.google.com/#q=$query")
    }

    private fun refreshAdapterData(result: List<ApplicationInfo>) {
        Log.d(LOG_TAG_DEBUG, "count: ${result.size}")

        searchResultAdapter.resultList = result.map {
            val name = packageManager.getApplicationLabel(it).toString()
            val iconDrawable = it.loadIcon(packageManager)
            AppInfo(this, name, iconDrawable, it.packageName)
        }
    }

    private fun searchApp(searchText: String): Observable<List<ApplicationInfo>>? {
        val result = applicationsInfoManager.appHashMap
                .filter {
                    it.key.startsWith(searchText, true)
                }.map { it.value }
        return Observable.just(result)
    }

    private fun calc(searchText: String): Observable<Float>? {
        val splitText = searchText.split(Regex("[\\+\\-\\*\\/]"))
        val operation = searchText.replace(Regex("[0-9]"), "")
        val para1 = splitText[0].toFloat()
        val para2 = splitText[1].toFloat()
        when (operation) {
            "+" -> {
                return Observable.just(para1 + para2)
            }
            "-" -> {
                return Observable.just(para1 - para2)
            }
            "*" -> {
                return Observable.just(para1 * para2)
            }
            "/" -> {
                return Observable.just(para1 / para2)
            }
            else -> {
                return Observable.just(0f)
            }
        }
    }

    //region [inner class]
    inner class SearchResultAdapter(var resultList: List<ResultInfo> = ArrayList())
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
                        result.resultClick()
                    }

            disposableMap[hashCode] = disposable
        }
    }
    // endregion


    // region [data class]
    abstract class ResultInfo(val name: String, val icon: Drawable?, val resultClick: ResultClick)

    class AppInfo(val activity: BaseActivity<*, *>, name: String, icon: Drawable?, private val packageName: String)
        : ResultInfo(
            name,
            icon, {
        val launchIntent = activity.packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent == null) {
            activity.toast(activity.getString(R.string.unable_open_no_ui_app)).show()
        } else {
            activity.startActivity(launchIntent)
            activity.finish()
        }
    })

    class SearchInfo(val activity: BaseActivity<*, *>, name: String, icon: Drawable?, searchUrl: Uri)
        : ResultInfo(
            name,
            icon, {
        activity.startActivity(Intent(Intent.ACTION_VIEW, searchUrl))
    })
    // endregion


    // region [model class]
    class ViewHolder(val viewDataBinding: ItemSearchResultBinding,
                     val vm: SearchResultItemViewModel = viewDataBinding.vm as SearchResultItemViewModel,
                     val tv: TextView = viewDataBinding.root.findViewById(R.id.tv_result)) :
            RecyclerView.ViewHolder(viewDataBinding.root)
    // endregion

}

