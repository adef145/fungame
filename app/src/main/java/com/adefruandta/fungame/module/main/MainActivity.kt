package com.adefruandta.fungame.module.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adefruandta.fungame.databinding.ActivityMainBinding
import com.adefruandta.fungame.module.game.GameActivity

class MainActivity : AppCompatActivity() {

    private val viewBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        viewBinding.buttonPlay.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }
    }
}