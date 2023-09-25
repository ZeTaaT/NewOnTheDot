package com.example.login.tabsAdaptor

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.login.LaterFragment
import com.example.login.PreferFragment
import com.example.login.TrendFragment

class TabsAdaptorer (activity : AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {

        when(position) {
            0 -> return TrendFragment()
            1 -> return LaterFragment()
            2 -> return PreferFragment()
        }
        return TrendFragment()
    }


    override fun getItemCount(): Int {
        return 3
    }



}