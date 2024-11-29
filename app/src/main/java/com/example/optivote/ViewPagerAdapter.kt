package com.example.optivote

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    companion object {
        private const val ITEM_COUNT = 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PersonalAccountFragment()
            1 -> HistoryFragment()
            2 -> VoteFragment()
            3 -> HomePageFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    override fun getItemCount(): Int {
        return ITEM_COUNT
    }
}
