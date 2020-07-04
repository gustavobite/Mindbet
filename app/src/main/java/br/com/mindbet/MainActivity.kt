package br.com.mindbet

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import br.com.mindbet.application.MindbetApp
import br.com.mindbet.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(){

    override fun getScreenName(): String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableCircularRevealTransition(root)

        setupViews()
    }

    private fun setupViews(){
        bottomNavigation.setupWithNavController(findNavController(R.id.container))
        bottomNavigation.setOnNavigationItemReselectedListener(null)
    }

}