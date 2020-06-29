package br.com.mindbet.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.mindbet.common.base.BaseActivity

class CoreActivity : BaseActivity() {

    override fun getScreenName(): String = "Core activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
