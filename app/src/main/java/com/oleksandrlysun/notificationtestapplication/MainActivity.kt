package com.oleksandrlysun.notificationtestapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FragmentListener {

    private val fragmentAdapter = FragmentAdapter(supportFragmentManager)
    private lateinit var notificationUtils: NotificationUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        handleArguments()
    }

    override fun onViewCreated(view: ManageFragmentView, number: Int) {
        view.setMinusButtonVisibility(number != 1)
    }

    override fun onCreateNotificationClick(number: Int) {
        notificationUtils.showNotification(number)
    }

    override fun onMinusClick() {
        notificationUtils.removeNotifications(fragmentAdapter.count)
        fragmentAdapter.removeFragmentAt(fragmentAdapter.count - 1)
        viewPager.currentItem = fragmentAdapter.count
    }

    override fun onPlusClick() {
        viewPager.currentItem = addFragment()
    }

    private fun handleArguments() {
        val number = intent.getIntExtra(KEY_FRAGMENT_NUMBER, -1)
        initViewPager(number)
    }

    private fun initViewPager(number: Int) {
        addFragment() // initial fragment
        if (number != -1) {
            (1 until number).forEach { _ ->
                addFragment()
            }
            viewPager.currentItem = number
        }
    }

    private fun setupUI() {
        notificationUtils = NotificationUtils(this)
        viewPager.adapter = fragmentAdapter
    }

    private fun addFragment(number: Int = fragmentAdapter.count + 1): Int {
        val fragment = MainFragment.newInstance(number).apply { setListener(this@MainActivity) }
        fragmentAdapter.addFragment(fragment)

        return number
    }

    companion object {

        const val KEY_FRAGMENT_NUMBER = "fragment_number"
    }
}