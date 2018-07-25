package com.android.example.github.testing

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.dai_pc.android_test.R

/**
 * fake activity for test single fragment
 */
class SingleFragmentActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentView = FrameLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            id = R.id.container
        }
        setContentView(contentView)
    }

    fun addFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction()
                .add(R.id.container,fragment,"")
                .commit()
    }

    fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .replace(R.id.container,fragment)
                .commit()
    }
}