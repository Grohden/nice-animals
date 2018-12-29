package com.grohden.niceanimals.ui.activities

import android.os.Bundle
import com.grohden.niceanimals.R
import com.grohden.niceanimals.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(appToolbar)
    }
}