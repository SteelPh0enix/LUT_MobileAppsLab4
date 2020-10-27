package com.example.myfrags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


/**
 * A simple [Fragment] subclass.
 * Use the [Fragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_1, container, false)

        // val = cannot change reference, var = normal variable, const = compile-time constant
        // mógłbym też zrobić tak (żeby określić typ zmiennej, ale tutaj mam od tego argument szablonu)
        // val buttonShuffle: Button = view.findViewById etc etc
        val buttonShuffle = view.findViewById<Button>(R.id.button_shuffle)
        val buttonClockwise = view.findViewById<Button>(R.id.button_clockwise)
        val buttonHide = view.findViewById<Button>(R.id.button_hide)
        val buttonRestore = view.findViewById<Button>(R.id.button_restore)

        // znacznie krótsze niż Javowe - kotlin do interfejsów jedno-metodowych pozwala przekazać
        // lambdę, a jeśli nie ma argumentów to pozwala skrócić ją do takiej konstrukcji
        // Resztę magii robi kompilator.
        buttonShuffle.setOnClickListener {
            // ? to null-check
            // jeśli callback jest null, zwróci null
            // jeśli nie, wykona funkcję
            // bardzo przydatne + można to chainować
            callback?.onButtonClickShuffle()
        }

        buttonClockwise.setOnClickListener {
            callback?.onButtonClickClockwise()
        }

        buttonHide.setOnClickListener {
            callback?.onButtonClickHide()
        }

        buttonRestore.setOnClickListener {
            callback?.onButtonClickRestore()
        }

        return view
    }

    // domyślnie jest public, private trzeba określić
    interface OnButtonClickListener {
        fun onButtonClickShuffle()
        fun onButtonClickClockwise()
        fun onButtonClickHide()
        fun onButtonClickRestore()
    }

    private var callback: OnButtonClickListener? = null

    fun setOnButtonClickListener(callback: OnButtonClickListener) {
        this.callback = callback
    }
}