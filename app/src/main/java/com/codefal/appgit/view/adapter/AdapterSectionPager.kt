package com.codefal.appgit.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.codefal.appgit.view.FollowerFragment
import com.codefal.appgit.view.FollowingFragment

class AdapterSectionPager (fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when(position){
            0 -> fragment = FollowingFragment()
            1 -> fragment = FollowerFragment()
        }
        return fragment
    }
}