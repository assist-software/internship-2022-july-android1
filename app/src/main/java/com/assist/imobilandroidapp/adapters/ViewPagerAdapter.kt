package com.assist.imobilandroidapp.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.assist.imobilandroidapp.screens.client.fragments.AllListingsFragment
import com.assist.imobilandroidapp.screens.client.fragments.MyListingsFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> AllListingsFragment()
            1 -> MyListingsFragment()
            else -> AllListingsFragment()
        }
    }

}