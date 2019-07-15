package com.oleksandrlysun.notificationtestapplication

interface FragmentListener {

    fun onViewCreated(view: ManageFragmentView, number: Int)

    fun onCreateNotificationClick(number: Int)

    fun onMinusClick()

    fun onPlusClick()
}