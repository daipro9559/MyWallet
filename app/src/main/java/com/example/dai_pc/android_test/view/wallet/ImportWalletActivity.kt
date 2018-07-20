package com.example.dai_pc.android_test.view.wallet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.EditorInfo
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseActivity
import com.example.dai_pc.android_test.databinding.ActivityImportWalletBinding
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_import_wallet.*

class ImportWalletActivity : BaseActivity<ActivityImportWalletBinding>() {

    private lateinit var importWalletViewModel: ImportWalletViewModel

    override fun getLayoutId() = R.layout.activity_import_wallet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableHomeHomeAsUp()
        setTitle(R.string.import_wallet)
        viewDataBinding.btnImport.setOnClickListener {
            clickImport()
        }
        importWalletViewModel = ViewModelProviders.of(this, viewModelFactory)[ImportWalletViewModel::class.java]
        importWalletViewModel.addressLiveData.observe(this, Observer {
            buildDialogSelectAccount(it!!)
        })

        viewDataBinding.edtNewPassword.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                clickImport()
            }
            true
        }
    }

    fun clickImport() {
        if (viewDataBinding.edtKeystore.text.isEmpty()) {
            viewDataBinding.edtKeystore.error = getString(R.string.not_empty)
            return
        }
        if (viewDataBinding.edtExportPassword.text.isEmpty()) {
            viewDataBinding.edtExportPassword.error = getString(R.string.not_empty)
            return
        }
        if (viewDataBinding.edtNewPassword.text.isEmpty()) {
            viewDataBinding.edtNewPassword.error = getString(R.string.not_empty)
            return
        }

        importWalletViewModel.importByKeyStore(viewDataBinding.edtKeystore.text.toString()
                , viewDataBinding.edtExportPassword.text.toString()
                , viewDataBinding.edtNewPassword.text.toString())
    }


    private fun buildDialogSelectAccount(address: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.import_completed)
                .setMessage(R.string.select_wallet_just_imported)
                .setPositiveButton(R.string.yes) { _, _ ->
                    importWalletViewModel.selectWalletImported(address)
                    finish()
                }
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.cancel()
                    finish()
                }
        builder.create().show()
    }

}