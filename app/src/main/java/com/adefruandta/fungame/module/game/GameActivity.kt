package com.adefruandta.fungame.module.game

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.adefruandta.fungame.databinding.ActivityGameBinding
import com.adefruandta.fungame.module.main.MainActivity


class GameActivity : AppCompatActivity() {

    private val viewBinding: ActivityGameBinding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }

    private val gameViewModel: GameViewModel by lazy {
        ViewModelProvider(this)[GameViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.viewPager.orientation = ORIENTATION_HORIZONTAL
        viewBinding.viewPager.isUserInputEnabled = false
        viewBinding.progressBar.visibility = View.VISIBLE
        gameViewModel.gamesLiveData.observe(this) {
            viewBinding.progressBar.visibility = View.GONE
            viewBinding.viewPager.adapter = GameAdapter(this).apply {
                gameSize = it.size
            }
        }
        gameViewModel.onGameDoneLiveData.observe(this) {
            if (viewBinding.viewPager.currentItem + 1 == viewBinding.viewPager.adapter?.itemCount ?: 0) {
                startActivity(Intent(this, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                })
            } else {
                viewBinding.viewPager.currentItem = viewBinding.viewPager.currentItem + 1
            }
        }
        gameViewModel.generateGame()
    }
}