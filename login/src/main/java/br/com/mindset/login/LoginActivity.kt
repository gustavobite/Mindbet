package br.com.mindset.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import br.com.mindbet.common.base.BaseActivity
import kotlinx.android.synthetic.main.actvity_login.*

class LoginActivity : BaseActivity() {
    override fun getScreenName(): String = "Login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_login)


        login.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
    
}