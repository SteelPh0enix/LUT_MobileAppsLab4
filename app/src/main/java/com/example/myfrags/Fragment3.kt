package com.example.myfrags

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment3.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment3 : Fragment() {
    private var fragsData: FragsData? = null
    private var numberObserver: Observer<Int>? = null

    private var text: TextView? = null
    private var button: Button? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_3, container, false)

        text = view.findViewById(R.id.current)
        button = view.findViewById(R.id.button_decrease)
        fragsData = ViewModelProvider(requireActivity())[FragsData::class.java]

        numberObserver = Observer {
            text?.text = it.toString()
        }

        fragsData!!.counter.observe(viewLifecycleOwner, numberObserver!!)

        button!!.setOnClickListener {
            val i: Int = fragsData!!.counter.value!!
            fragsData!!.counter.value = i - 1
        }

        return view
    }

}