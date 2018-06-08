package com.cbstudio.blackbutler.main.search.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.cbstudio.blackbutler.R
import com.cbstudio.blackbutler.databinding.ActivitySearchBinding
import com.cbstudio.blackbutler.main.base.activity.BaseActivity
import com.cbstudio.blackbutler.main.search.vm.SearchViewModel

class SearchActivity : BaseActivity<SearchViewModel, ActivitySearchBinding>
(
        SearchViewModel::class
        , R.layout.activity_search
) {

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
    }
}
