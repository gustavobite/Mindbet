package br.com.mindbet

import android.os.Bundle
import br.com.mindbet.application.MindbetApp
import br.com.mindbet.common.base.BaseActivity

class MainActivity : BaseActivity(){

    override fun getScreenName(): String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}