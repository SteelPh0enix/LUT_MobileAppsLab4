package com.example.myfrags

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


/**
 * A simple [Fragment] subclass.
 * Use the [Fragment4.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment4 : Fragment() {
    private var fragsData: FragsData? = null
    private var numberObserver: Observer<Int>? = null

    private var edit: EditText? = null
    private var turnOffWatcher = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_4, container, false)

        edit = view.findViewById(R.id.editTextNumber)
        fragsData = ViewModelProvider(requireActivity())[FragsData::class.java]

        numberObserver = Observer {
            turnOffWatcher = true
            edit?.setText(it.toString())
        }

        fragsData!!.counter.observe(viewLifecycleOwner, numberObserver!!)

        edit!!.addTextChangedListener {
            if (!turnOffWatcher) {
                val currentSelection = edit!!.selectionStart
                val currentLength = edit!!.text.length
                Log.d("TextListener", "Current selection: $currentSelection")
                Log.d("TextListener", "Current length: $currentLength")

                if (currentLength > 0 && edit!!.text.toString() != "-") {
                    val i = try {
                        it.toString().toInt()
                    } catch (e: NumberFormatException) {
                        fragsData!!.counter.value!!
                    }

                    fragsData!!.counter.value = i
                }

                if (currentSelection != currentLength) {
                    edit!!.setSelection(currentSelection)
                } else {
                    edit!!.setSelection(edit!!.text.length)
                }
            } else {
                turnOffWatcher = !turnOffWatcher
            }
        }

        return view
    }

}