package br.com.beertime.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.beertime.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Artur on 26/10/2019.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        GlobalScope.launch {
            delay(1000)
            startActivity(Intent(this@SplashActivity, BeerListActivity::class.java))
            finish()
        }

        /*Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, BeerListActivity::class.java))
            finish()
        }, 1000)*/
    }
}