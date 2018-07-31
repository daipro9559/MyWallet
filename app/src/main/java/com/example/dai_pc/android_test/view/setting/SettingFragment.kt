package com.example.dai_pc.android_test.view.setting

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.*
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.repository.NetworkRepository
import com.example.dai_pc.android_test.repository.WalletRepository
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject

class SettingFragment : PreferenceFragmentCompat(),SharedPreferences.OnSharedPreferenceChangeListener{
    @Inject lateinit var networkRepository: NetworkRepository
    @Inject lateinit var walletRepository: WalletRepository
    @Inject lateinit var preferenceHelper: PreferenceHelper
    companion object {
        fun newsInstance() : SettingFragment{
            val bundle = Bundle()
            val settingFragment = SettingFragment()
            settingFragment.arguments = bundle
            return settingFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.setting)
        initDataWallet()
        initDataNetwork()
        val requirePass = findPreference(Constant.KEY_REQUIRE_PASSWORD) as SwitchPreferenceCompat
        requirePass.setOnPreferenceChangeListener { _, newValue ->
            preferenceHelper.putBoolean(Constant.KEY_REQUIRE_PASSWORD,newValue as Boolean)
            true
        }

    }

    fun initDataNetwork(){
        val listNetWork = networkRepository.listNetWorkProvier
        val entries = arrayOfNulls<CharSequence>(listNetWork.size)
        val entryvalues = arrayOfNulls<CharSequence>(listNetWork.size)
        val  networkPreferences = findPreference(context!!.getString(R.string.network_key)) as ListPreference
        networkPreferences.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->
            Timber.i(newValue.toString())
            networkRepository.changeNetworkSelect(newValue.toString().toInt())
            preferenceHelper.putInt(context!!.getString(R.string.network_key),newValue.toString().toInt())
            initDataNetwork()
            true
        }


        for (i in 0 until listNetWork.size){
            entries[i] = listNetWork[i].name
            entryvalues[i] = listNetWork[i].id.toString()
        }
        networkPreferences.setDefaultValue(networkRepository.networkProviderSelected)
        networkPreferences.value = networkRepository.networkProviderSelected.id.toString()
        networkPreferences.entries = entries
        networkPreferences.entryValues = entryvalues
        networkPreferences.summary = networkRepository.networkProviderSelected.name
    }

    fun initDataWallet(){
        walletRepository.getAllAccount()
        val  walletPreferences= findPreference(context!!.getString(R.string.account_key)) as ListPreference
        val entries = arrayOfNulls<CharSequence>(walletRepository.accountsLiveData.value!!.size)
        for (i in 0 until walletRepository.accountsLiveData.value!!.size ){
            entries[i] = walletRepository.accountsLiveData.value!![i].address.hex.toString()
        }
        walletPreferences.setDefaultValue(walletRepository.initAccountSelect())
        walletPreferences.value = walletRepository.accountSelected.value
        walletPreferences.entries = entries
        walletPreferences.entryValues = entries
        walletPreferences.summary = walletRepository.accountSelected.value
        walletPreferences.onPreferenceChangeListener = Preference.OnPreferenceChangeListener{_,newValue ->
            walletRepository.saveAccountSelect(newValue.toString())
            walletRepository.initAccountSelect()
            initDataWallet()
            true
        }

    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        when(p1){
            context!!.getString(R.string.network_key) ->{
            // need reload wallet content
            }
            context!!.getString(R.string.account_key) ->{
                // need reload wallet content
            }
        }
    }
}