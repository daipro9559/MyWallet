package com.example.dai_pc.android_test.view.setting

import android.os.Bundle
import android.preference.PreferenceActivity
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.dai_pc.android_test.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_setting.*
import javax.inject.Inject

class SettingActivity :AppCompatActivity(),HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingFragment: DispatchingAndroidInjector<Fragment>
    override fun supportFragmentInjector() = dispatchingFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(toolBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.setting)
        supportFragmentManager.beginTransaction().add(R.id.contain_fragment,SettingFragment.newsInstance(),"").commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId){
            android.R.id.home ->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}