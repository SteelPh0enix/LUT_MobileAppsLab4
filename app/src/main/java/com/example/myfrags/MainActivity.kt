package com.example.myfrags

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity(), Fragment1.OnButtonClickListener {

    // Array<T> zamiast T[], używa się bardzo podobnie
    private var frames: IntArray? = null
    private var hidden: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            frames = intArrayOf(R.id.frame1, R.id.frame2, R.id.frame3, R.id.frame4)
            hidden = false

            val fragments = arrayOf(Fragment1(), Fragment2(), Fragment3(), Fragment4())
            val transaction = supportFragmentManager.beginTransaction()

            for (i in 0..3) {
                // !! powoduje że instrukcja zwróci wyjątek jeśli obiekt do którego się odnosimy jest null
                transaction.add(frames!![i], fragments[i])
            }

            transaction.addToBackStack(null)
            transaction.commit()

        } else {
            frames = savedInstanceState.getIntArray("FRAMES")
            hidden = savedInstanceState.getBoolean("HIDDEN")
        }
    }

    private fun createNewFragments() {
        val newFragments = arrayOf(Fragment1(), Fragment2(), Fragment3(), Fragment4())

        val transaction = supportFragmentManager.beginTransaction()
        for (i in 0..3) {
            transaction.replace(frames!![i], newFragments[i])
            if (hidden && (newFragments[i] !is Fragment1)) transaction.hide(newFragments[i])
        }

        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putIntArray("FRAMES", frames)
        outState.putBoolean("HIDDEN", hidden)
    }

    override fun onButtonClickRestore() {
        if (!hidden) return

        val transaction = supportFragmentManager.beginTransaction()
        for (fragment in supportFragmentManager.fragments) {
            if (fragment is Fragment1) continue
            transaction.show(fragment)
        }

        transaction.addToBackStack(null)
        transaction.commit()
        hidden = false
    }

    override fun onButtonClickClockwise() {
        // BUG: po shufflowaniu nie ma pewności że fragmenty będą
        // przesuwać się clockwise
        val temp = frames!![0]
        frames!![0] = frames!![1]
        frames!![1] = frames!![2]
        frames!![2] = frames!![3]
        frames!![3] = temp

        createNewFragments()
    }

    override fun onButtonClickHide() {
        if (hidden) return

        val transaction = supportFragmentManager.beginTransaction()
        for (fragment in supportFragmentManager.fragments) {
            if (fragment is Fragment1) continue

            transaction.hide(fragment)
        }

        transaction.addToBackStack(null)
        transaction.commit()
        hidden = true
    }

    override fun onButtonClickShuffle() {
        // tutaj powinien być zrobiony check przed tworzeniem listy, ale jestem leniwy
        // jak walnie nullexception to walnie nullexception, na co drążyć temat ( ͡° ͜ʖ ͡°)
        val list = frames!!.slice(0..3).shuffled()
        for (i in 0..3) {
            frames!![i] = list[i]
        }

        createNewFragments()
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

        if (fragment is Fragment1) {
            fragment.setOnButtonClickListener(this)
        }
    }
}