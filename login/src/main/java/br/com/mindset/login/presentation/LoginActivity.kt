package br.com.mindset.login.presentation

import android.app.Activity
import android.os.Bundle
import br.com.mindbet.common.base.BaseActivity
import br.com.mindset.login.R
import kotlinx.android.synthetic.main.actvity_login.*
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.android.inject

@InternalCoroutinesApi
class LoginActivity : BaseActivity() {
//    private val viewModel : LoginViewModel by inject()

    override fun getScreenName(): String = "Login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_login)


        login.setOnClickListener {
//            viewModel.doLogin()
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
    
}