package com.oleksandrlysun.notificationtestapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), FragmentListener {

    private val fragmentAdapter = FragmentAdapter(supportFragmentManager)

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
       TODO()
    }

    override fun onMinusClick() {
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
        addFragment() // fragment with number "1"
    }

    private fun setupUI() {
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
