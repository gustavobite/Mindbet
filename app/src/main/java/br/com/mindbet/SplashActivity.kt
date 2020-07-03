package br.com.mindbet

import android.content.Intent
import android.os.Bundle
import br.com.mindbet.common.base.BaseActivity
import br.com.mindbet.core.CoreActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity(){
    override fun getScreenName(): String = "Splash"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        launch {
            delay(3000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }
    }
}