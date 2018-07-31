package com.example.dai_pc.android_test.view.accounts

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseActivity
import com.example.dai_pc.android_test.databinding.ActivityManageAccountBinding
import com.example.dai_pc.android_test.entity.Resource
import com.example.dai_pc.android_test.ultil.Ultil
import com.example.dai_pc.android_test.view.main.address.MyAddressViewModel
import com.example.dai_pc.android_test.view.main.address.SHARE_REQUEST_CODE
import timber.log.Timber

class ManageAccountActivity : BaseActivity<ActivityManageAccountBinding>() {

    private lateinit var manageAccountViewModel: ManageAccountViewModel
    private lateinit var myAddressViewModel: MyAddressViewModel
    private lateinit var adapter: AccountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        manageAccountViewModel = ViewModelProviders.of(this, viewModelFactory)[ManageAccountViewModel::class.java]
        myAddressViewModel = ViewModelProviders.of(this, viewModelFactory)[MyAddressViewModel::class.java]
        manageAccountViewModel.getAllAccount()
        manageAccountViewModel.listAccountLiveData.observe(this, Observer {
            Timber.e("" + it!!.size)
            adapter.swapListItem(it)
        })
        manageAccountViewModel.errorLiveData.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            Timber.e(it)
        })
        manageAccountViewModel.deletedAccountNotify.observe(this, Observer {
            if (it!!.status == Resource.Status.SUCCESS) {
                Toast.makeText(this, it.t, Toast.LENGTH_LONG).show()
                manageAccountViewModel.getAllAccount()
            } else if (it!!.status == Resource.Status.ERROR) {
                Toast.makeText(this, it.messError, Toast.LENGTH_LONG).show()

            }
        })

    }

    override fun getLayoutId() = R.layout.activity_manage_account
    private fun initView() {
        enableHomeHomeAsUp()
        setTitle(R.string.manage_account)
        viewDataBinding.recycleView.addItemDecoration(DividerItemDecoration(this!!.applicationContext, LinearLayoutManager.VERTICAL))
        adapter = AccountAdapter { menuId, address ->
            handleMenuClick(menuId, address)
        }
        viewDataBinding.recycleView.adapter = adapter
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
                Toast.makeText(applicationContext,getString(R.string.select_account_completed),Toast.LENGTH_LONG).show()
            }
            R.id.delete -> {
                Ultil.showDialogInputPassword(this, getString(R.string.delete_account), getString(R.string.delete)) {
                    manageAccountViewModel.deleteAccount(address, it)
                }
            }
        }
    }

    private fun createDialogExport() {
        val view = layoutInflater.inflate(R.layout.dialog_create_wallet, null)
        val textInputEditText = view.findViewById<TextInputEditText>(R.id.edt_pass)
        val builder = AlertDialog.Builder(this!!)
                .setTitle(R.string.export_account)
                .setView(view)
                .setNegativeButton(R.string.cancel) { dialogInterface, _ ->
                    dialogInterface.cancel()
                    dialogInterface.dismiss()
                }
                .setPositiveButton(R.string.export) { _, i ->
                    myAddressViewModel.export(textInputEditText.text.toString().trim())
                    myAddressViewModel.liveDataExport.observe(this, Observer {
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

}