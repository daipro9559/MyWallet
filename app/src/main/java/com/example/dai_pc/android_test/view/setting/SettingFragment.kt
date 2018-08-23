package com.example.dai_pc.android_test.view.setting

import android.annotation.SuppressLint
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

class SettingFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    @Inject
    lateinit var networkRepository: NetworkRepository
    @Inject
    lateinit var walletRepository: WalletRepository
    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    companion object {
        fun newsInstance(): SettingFragment {
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

    override fun onDisplayPreferenceDialog(preference: Preference?) {
        super.onDisplayPreferenceDialog(preference)

    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.setting)

        initDataPlatform()
        initDataNetwork()
        val requirePass = findPreference(Constant.KEY_REQUIRE_PASSWORD) as SwitchPreferenceCompat
        requirePass.setOnPreferenceChangeListener { _, newValue ->
            preferenceHelper.putBoolean(Constant.KEY_REQUIRE_PASSWORD, newValue as Boolean)
            true
        }

    }

    private fun initDataNetwork() {
        val networkPreferences = findPreference(Constant.KEY_NETWORK_ETHER) as ListPreference
        if (preferenceHelper.getPlatform() == Constant.ETHEREUM_PLATFORM){
            val listNetWork = networkRepository.listNetWorkProvier
            val entries = arrayOfNulls<CharSequence>(listNetWork.size)
            val entryvalues = arrayOfNulls<CharSequence>(listNetWork.size)
            networkPreferences.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->
                Timber.i(newValue.toString())
                preferenceHelper.putInt(Constant.KEY_NETWORK_ETHER, newValue.toString().toInt())
                networkRepository.changeNetworkSelect()
                initDataNetwork()
                true
            }


            for (i in 0 until listNetWork.size) {
                entries[i] = listNetWork[i].name
                entryvalues[i] = listNetWork[i].id.toString()
            }
            networkPreferences.setDefaultValue(networkRepository.networkProviderSelected)
            networkPreferences.value = networkRepository.networkProviderSelected.id.toString()
            networkPreferences.entries = entries
            networkPreferences.entryValues = entryvalues
            networkPreferences.summary = networkRepository.networkProviderSelected.name
        }else if (preferenceHelper.getPlatform() == Constant.STELLAR_PLATFORM){
            val entries = arrayOfNulls<CharSequence>(2)
            val entryValues = arrayOfNulls<CharSequence>(2)
            networkPreferences.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->
                Timber.i(newValue.toString())
                preferenceHelper.putString(Constant.KEY_NETWORK_STELLAR, newValue.toString())
                networkRepository.changeNetworkSelect()
                initDataNetwork()
                true
            }
            val default = if (preferenceHelper.getString(Constant.KEY_NETWORK_STELLAR) == Constant.STELLAR_MAIN_NET_URl) getString(R.string.main_net) else getString(R.string.test_net)
            networkPreferences.setDefaultValue(default)
            entries[0] = getString(R.string.main_net)
            entries[1] = getString(R.string.test_net)
            entryValues[0]= Constant.STELLAR_MAIN_NET_URl
            entryValues[1]= Constant.STELLAR_TEST_NET_URL
            networkPreferences.value = if (preferenceHelper.getString(Constant.KEY_NETWORK_STELLAR) == Constant.STELLAR_MAIN_NET_URl) Constant.STELLAR_MAIN_NET_URl else Constant.STELLAR_TEST_NET_URL
            networkPreferences.entries = entries
            networkPreferences.entryValues = entryValues
            networkPreferences.summary = default
        }

    }

    private fun initDataPlatform() {
        val platform = findPreference(Constant.PLATFORM_KEY) as ListPreference
        val entries = arrayOfNulls<CharSequence>(2)
        val entryValues = arrayOfNulls<CharSequence>(2)
        entries[0] = getString(R.string.ethereum)
        entries[1] = getString(R.string.stellar)
        entryValues[0]= Constant.ETHEREUM_PLATFORM
        entryValues[1]= Constant.STELLAR_PLATFORM
        val default = if (preferenceHelper.getPlatform() == Constant.ETHEREUM_PLATFORM) getString(R.string.ethereum) else getString(R.string.stellar)
        platform.setDefaultValue(default)
        platform.value = if (preferenceHelper.getPlatform() == Constant.ETHEREUM_PLATFORM) Constant.ETHEREUM_PLATFORM else Constant.STELLAR_PLATFORM
        platform.entries = entries
        platform.entryValues = entryValues
        platform.summary = default
        platform.setOnPreferenceChangeListener{preference, newValue ->
            preferenceHelper.putString(Constant.PLATFORM_KEY,newValue.toString())
            initDataPlatform()
            initDataNetwork()
            true
        }
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {

    }
}