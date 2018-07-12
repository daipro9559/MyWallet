package com.example.dai_pc.android_test.view.setting

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.ListPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceScreen
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.repository.NetworkRepository
import com.example.dai_pc.android_test.repository.WalletRepository
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import dagger.android.support.AndroidSupportInjection
import jnr.ffi.provider.converters.CharSequenceParameterConverter
import kotlinx.android.synthetic.main.activity_main.view.*
import javax.inject.Inject

class SettingFragment : PreferenceFragmentCompat(),SharedPreferences.OnSharedPreferenceChangeListener{
    @Inject lateinit var networkRepository: NetworkRepository
    @Inject lateinit var walletRepository: WalletRepository
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
    }

    fun initDataNetwork(){
        val listNetWork = networkRepository.listNetWorkProvier
        val entries = arrayOfNulls<CharSequence>(listNetWork.size)
        val entryvalues = arrayOfNulls<CharSequence>(listNetWork.size)
        val  networkPreferences = findPreference(context!!.getString(R.string.network_key)) as ListPreference

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
        val  walletPreferences= findPreference(context!!.getString(R.string.wallet_key)) as ListPreference
        val entries = arrayOfNulls<CharSequence>(walletRepository.accountsLiveData!!.value!!.size)
        for (i in 0 until walletRepository.accountsLiveData!!.value!!.size ){
            entries[i] = walletRepository.accountsLiveData!!.value!![i].address.hex.toString()
        }
        walletPreferences.setDefaultValue(walletRepository.getAccountSetting())
        walletPreferences.value = walletRepository.getAccountSetting()
        walletPreferences.entries = entries
        walletPreferences.entryValues = entries
        walletPreferences.summary = walletRepository.getAccountSetting()

    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        when(p1){
            context!!.getString(R.string.network_key) ->{
                networkRepository.changeNetworkSelect(p0!!.getInt(p1,1))
            }
        }
    }

}