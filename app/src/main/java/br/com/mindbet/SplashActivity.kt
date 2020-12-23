package br.com.mindbet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import br.com.mindbet.common.base.BaseActivity
import br.com.mindset.login.presentation.LoginActivity
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class SplashActivity : BaseActivity(){

    companion object{
        const val LOGIN_RESULT_CODE = 9983
    }

    override fun getScreenName(): String = "Splash"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        launch {
            delay(3000)
            startActivityForResult(Intent(this@SplashActivity, LoginActivity::class.java),LOGIN_RESULT_CODE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LOGIN_RESULT_CODE){
            if(resultCode == Activity.RESULT_OK){
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
        }
    }
}