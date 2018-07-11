package com.example.dai_pc.android_test.view.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter( fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val list :ArrayList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment = list[position]

    override fun getCount(): Int = list.size

    fun addFragment(fragment: Fragment){
        list.add(fragment)
        notifyDataSetChanged()
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return super.getPageTitle(position)
    }
}