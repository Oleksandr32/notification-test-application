package com.oleksandrlysun.notificationtestapplication

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class FragmentAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = mutableListOf<Fragment>()

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

    override fun getItemPosition(`object`: Any): Int {
        return if (fragments.contains(`object`)) fragments.indexOf(`object`) else POSITION_NONE
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
        notifyDataSetChanged()
    }

    fun removeFragmentAt(position: Int) {
        fragments.removeAt(position)
        notifyDataSetChanged()
    }
}