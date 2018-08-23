package com.example.dai_pc.android_test.view.accounts

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.databinding.ActivityManageAccountBinding
import com.example.dai_pc.android_test.entity.Resource
import com.example.dai_pc.android_test.ultil.Callback
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import com.example.dai_pc.android_test.ultil.Ultil
import com.example.dai_pc.android_test.view.main.MainFragment
import com.example.dai_pc.android_test.view.main.address.BottomSheetFragment
import com.example.dai_pc.android_test.view.main.address.MyAddressViewModel
import com.example.dai_pc.android_test.view.main.address.SHARE_REQUEST_CODE
import com.example.dai_pc.android_test.view.wallet.ImportWalletActivity
import com.example.stellar.KeyPair
import kotlinx.android.synthetic.main.fragment_my_address.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import timber.log.Timber
import javax.inject.Inject

class ManageAccountFragment : MainFragment<ActivityManageAccountBinding>() {


    override fun getlayoutId() = R.layout.activity_manage_account
    private lateinit var manageAccountViewModel: ManageAccountViewModel
    private lateinit var stellarAccountAdapter: StellarAccountAdapter
    private lateinit var etherAccountAdapter: EtherAccountAdapter

    @Inject
    lateinit var preferenceHelper: PreferenceHelper
    lateinit var callback: Callback<String>

    companion object {
        fun newInstance(): ManageAccountFragment {
            var bundle = Bundle()
            var manageAccountFragment = ManageAccountFragment()
            manageAccountFragment.arguments = bundle
            return manageAccountFragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        manageAccountViewModel = ViewModelProviders.of(this, viewModelFactory)[ManageAccountViewModel::class.java]
        manageAccountViewModel.listAccountStellar.observe(this, Observer {
            viewDataBinding.recycleView.adapter = stellarAccountAdapter
            Timber.e("" + it!!.size)
            stellarAccountAdapter.swapListItem(it)
        })
        manageAccountViewModel.listAccountEther.observe(this, Observer {
            viewDataBinding.recycleView.adapter = etherAccountAdapter
            etherAccountAdapter.swapListItem(it!!)

        })
        manageAccountViewModel.errorLiveData.observe(this, Observer {
            Toast.makeText(activity!!.applicationContext, it, Toast.LENGTH_LONG).show()
            Timber.e(it)
        })
        manageAccountViewModel.deletedAccountNotify.observe(this, Observer {
            if (it!!.status == Resource.Status.SUCCESS) {
                Toast.makeText(activity!!.applicationContext, it.t, Toast.LENGTH_LONG).show()
                manageAccountViewModel.getAllAccount()
            } else if (it!!.status == Resource.Status.ERROR) {
                Toast.makeText(activity!!.applicationContext, it.messError, Toast.LENGTH_LONG).show()

            }
        })
    }

    private fun initView() {
        stellarAccountAdapter = StellarAccountAdapter { menuId, address ->
            handleMenuClick(menuId, address)
        }
        etherAccountAdapter = EtherAccountAdapter() { menuId, address ->

        }
        viewDataBinding.btnAddAccount.setOnClickListener {
            clickAddAddress()
        }
    }

    private fun handleMenuClick(menuId: Int, address: String) {
        when (menuId) {
            R.id.edit -> {
            }
            R.id.export -> {
                createDialogExport()
            }
            R.id.select -> {
                manageAccountViewModel.selectAccount(address)
                Toast.makeText(activity!!.applicationContext, getString(R.string.select_account_completed), Toast.LENGTH_LONG).show()
            }
            R.id.delete -> {
                Ultil.showDialogInputPassword(activity!!, getString(R.string.delete_account), getString(R.string.delete)) {
                    manageAccountViewModel.deleteAccount(address, it)
                }
            }
        }
    }

    private fun clickAddAddress() {
        val bottomSheetFragment = BottomSheetFragment()
        val platform = preferenceHelper.getPlatform()
        bottomSheetFragment.callback = {
            if (it.id == R.id.create_wallet) {
                fragmentManager!!.beginTransaction().remove(fragmentManager!!.findFragmentByTag("bottom_sheet")).commit()
                if (platform == Constant.STELLAR_PLATFORM) {
                    manageAccountViewModel.createAccountStellar()
                    manageAccountViewModel.liveDataAccountStellar.observe(this, Observer {
                        Toast.makeText(context!!.applicationContext, "create account stellar completed", Toast.LENGTH_LONG).show()
                        refresh()
                    })
                } else if (platform == Constant.ETHEREUM_PLATFORM) {
                    buildDialogCreateWallet()
                }

            } else {
                fragmentManager!!.beginTransaction().remove(fragmentManager!!.findFragmentByTag("bottom_sheet")).commit()
                if (platform == Constant.STELLAR_PLATFORM){

                }else {
                    startActivity(Intent(activity!!, ImportWalletActivity::class.java))
                }
            }
        }
        fragmentManager!!.beginTransaction().add(bottomSheetFragment, "bottom_sheet").disallowAddToBackStack().commit()
    }

    private fun buildDialogCreateWallet() {
        var alertDialog: AlertDialog? = null
        val builder = AlertDialog.Builder(activity!!,R.style.MyAlertDialogStyle)
                .setTitle(R.string.create_account)
                .setView(R.layout.dialog_create_wallet)
                .setNegativeButton(R.string.cancel) { dialogInterface, _ ->
                    dialogInterface.cancel()
                    dialogInterface.dismiss()
                }
                .setPositiveButton(R.string.create) { _, i ->
                    val editInput: TextInputEditText = alertDialog!!.findViewById<TextInputEditText>(R.id.edt_pass)!!
                    if (editInput.text.isEmpty()) {
                        editInput.error = context!!.getString(R.string.no_input_password)
                    } else {
                        manageAccountViewModel.createAccount(editInput.text.toString())
                        manageAccountViewModel.liveDataAccount.observe(this, Observer {
                            it?.let {
                                val account = it
                                Toast.makeText(context!!.applicationContext, context!!.getString(R.string.create_completed), Toast.LENGTH_LONG).show()
                                // if no have account , set account was have
                                manageAccountViewModel.selectWallet(it!!.address.hex.toString())
                                callback?.let {
                                    callback(account!!.address.hex.toString())
                                }
                                manageAccountViewModel.getAllAccount()
                            }
                        })
                    }
                }
        alertDialog = builder.create()
        alertDialog.show()
    }


    private fun createDialogExport() {
        val view = layoutInflater.inflate(R.layout.dialog_create_wallet, null)
        val textInputEditText = view.findViewById<TextInputEditText>(R.id.edt_pass)
        val builder = AlertDialog.Builder(activity!!)
                .setTitle(R.string.export_account)
                .setView(view)
                .setNegativeButton(R.string.cancel) { dialogInterface, _ ->
                    dialogInterface.cancel()
                    dialogInterface.dismiss()
                }
                .setPositiveButton(R.string.export) { _, i ->
                    manageAccountViewModel.export(textInputEditText.text.toString().trim())
                    manageAccountViewModel.liveDataExport.observe(this, Observer {
                        openShareDialog(it!!)
                    })
                }
        builder.create().show()
    }

    private fun openShareDialog(jsonData: String) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Keystore")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, jsonData)
        startActivityForResult(
                Intent.createChooser(sharingIntent, "Share via"),
                SHARE_REQUEST_CODE)
    }

    override fun refresh() {
        manageAccountViewModel.getAllAccount()
    }

    override fun changeNetwork() {
    }

    override fun changeAccount() {
    }

    override fun changePlatform() {

    }
}