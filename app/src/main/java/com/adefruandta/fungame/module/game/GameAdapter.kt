package com.adefruandta.fungame.module.game

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class GameAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    var gameSize: Int = 0
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return gameSize
    }

    override fun createFragment(position: Int): Fragment {
        return GameFragment.create(position)
    }
}