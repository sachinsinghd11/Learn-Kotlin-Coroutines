package com.sachin_singh_dighan.learn_kotlin_coroutines.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sachin_singh_dighan.learn_kotlin_coroutines.R
import com.sachin_singh_dighan.learn_kotlin_coroutines.ui.basic.BasicActivity
import com.sachin_singh_dighan.learn_kotlin_coroutines.ui.retrofit.series.SeriesNetworkCallsActivity
import com.sachin_singh_dighan.learn_kotlin_coroutines.ui.retrofit.single.SingleNetworkCallActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startSingleNetworkCallActivity(view: View){
        startActivity(Intent(this@MainActivity, SingleNetworkCallActivity::class.java))
    }

    fun startSeriesNetworkCallsActivity(view: View){
        startActivity(Intent(this@MainActivity, SeriesNetworkCallsActivity::class.java))
    }

    fun startBasicActivity(view: View) {
        startActivity(Intent(this@MainActivity, BasicActivity::class.java))
    }
}