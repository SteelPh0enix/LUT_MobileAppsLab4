package com.example.myfrags

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment1 = Fragment1()
        val fragment2 = Fragment2()
        val fragment3 = Fragment3()
        val fragment4 = Fragment4()

        val transaction = supportFragmentManager.beginTransaction()

        transaction.add(R.id.frame1, fragment1)
        transaction.add(R.id.frame2, fragment2)
        transaction.add(R.id.frame3, fragment3)
        transaction.add(R.id.frame4, fragment4)

        transaction.addToBackStack(null)
        transaction.commit()
    }
}