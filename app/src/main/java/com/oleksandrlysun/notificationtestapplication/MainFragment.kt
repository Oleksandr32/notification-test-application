package com.oleksandrlysun.notificationtestapplication

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), ManageFragmentView {

    private var number: Int? = null
    private var listener: FragmentListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleArguments()
        setupUI()
        listener?.onViewCreated(this, number!!)
    }

    override fun setMinusButtonVisibility(isVisible: Boolean) {
        minusButton?.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    fun setListener(listener: FragmentListener) {
        this.listener = listener
    }

    private fun handleArguments() {
        arguments?.let {
            number = it.getInt(KEY_NUMBER)
            numberTextView.text = number.toString()
        }
    }

    private fun setupUI() {
        createNotificationButton.setOnClickListener { listener?.onCreateNotificationClick(number!!) }
        plusButton.setOnClickListener { listener?.onPlusClick() }
        minusButton.setOnClickListener { listener?.onMinusClick() }
    }

    companion object {
        private const val KEY_NUMBER = "number"

        @JvmStatic
        fun newInstance(number: Int): MainFragment {
            return MainFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_NUMBER, number)
                }
            }
        }
    }
}