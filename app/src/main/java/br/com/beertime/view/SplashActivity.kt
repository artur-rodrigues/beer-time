package br.com.beertime.view

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.beertime.R
import br.com.beertime.databinding.ActivitySplashBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Artur on 26/10/2019.
 */
class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        binding.labelBeer.text = createSplashText(R.string.label_splash_beer)
        binding.labelTime.text = createSplashText(R.string.label_splash_time)

        GlobalScope.launch {
            delay(1000)
            startActivity(Intent(this@SplashActivity, BeerListActivity::class.java))
            finish()
        }
    }

    private fun createSplashText(@StringRes id: Int): SpannableString {
        val spannableLabelBeer = SpannableString(getString(id))
        spannableLabelBeer.setSpan(RelativeSizeSpan(2f), 0, 1, 0) // set size
        return spannableLabelBeer
    }
}