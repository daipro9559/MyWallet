package com.example.dai_pc.android_test.view.wallet

import android.animation.Animator
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.inputmethod.EditorInfo
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseActivity
import com.example.dai_pc.android_test.databinding.ActivityImportWalletBinding

class ImportWalletActivity : BaseActivity<ActivityImportWalletBinding>() {

    private val ANIMATION_DURATION = 500L
    private lateinit var importWalletViewModel: ImportWalletViewModel
    private var isImportByPrivateKey = false

    override fun getLayoutId() = R.layout.activity_import_wallet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableHomeHomeAsUp()
        setTitle(R.string.import_account_by_keystore)
        viewDataBinding.btnImport.setOnClickListener {
            clickImport()
        }
        importWalletViewModel = ViewModelProviders.of(this, viewModelFactory)[ImportWalletViewModel::class.java]
        importWalletViewModel.addressLiveData.observe(this, Observer {
            buildDialogSelectAccount(it!!)
        })
        importWalletViewModel.addressByPrivateKeyLiveData.observe(this, Observer {
            buildDialogSelectAccount(it!!)
        })

        viewDataBinding.edtNewPassword.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                clickImport()
            }
            true
        }
        viewDataBinding.edtNewPasswordPrivateKey.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                clickImport()
            }
            true
        }
    }

    fun clickImport() {
        if (!isImportByPrivateKey) {
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
        } else {
            if (viewDataBinding.edtPrivateKey.text.isEmpty()) {
                viewDataBinding.edtPrivateKey.error = getString(R.string.not_empty)
                return
            }
            if (viewDataBinding.edtNewPasswordPrivateKey.text.isEmpty()) {
                viewDataBinding.edtNewPasswordPrivateKey.error = getString(R.string.not_empty)
                return
            }
            importWalletViewModel.importByPrivateKey(viewDataBinding.edtPrivateKey.text.toString()
                    , viewDataBinding.edtNewPasswordPrivateKey.text.toString())

        }
    }


    private fun buildDialogSelectAccount(address: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.import_completed)
                .setMessage(R.string.select_account_just_imported)
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

    private fun hideLayoutKeyStore() {
        YoYo.with(Techniques.SlideOutDown)
                .duration(ANIMATION_DURATION)
                .interpolate(LinearInterpolator())
                .withListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(p0: Animator?) {
                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        viewDataBinding.layoutImportPrivateKey.visibility = View.VISIBLE
                    }

                    override fun onAnimationCancel(p0: Animator?) {
                    }

                    override fun onAnimationStart(p0: Animator?) {
                    }

                })
                .playOn(viewDataBinding.viewKeyStore)
        isImportByPrivateKey = true
        setTitle(R.string.import_account_by_private_key)

    }

    private fun showLayoutKeyStore() {
        YoYo.with(Techniques.SlideInUp)
                .duration(ANIMATION_DURATION)
                .interpolate(LinearInterpolator())
                .withListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(p0: Animator?) {
                    }

                    override fun onAnimationEnd(p0: Animator?) {
                    }

                    override fun onAnimationCancel(p0: Animator?) {
                    }

                    override fun onAnimationStart(p0: Animator?) {
                        viewDataBinding.layoutImportPrivateKey.visibility = View.INVISIBLE
                    }

                })
                .playOn(viewDataBinding.viewKeyStore)
        isImportByPrivateKey = false
        setTitle(R.string.import_account_by_keystore)

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.test, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.swipeImport -> {
                if (isImportByPrivateKey) {
                    showLayoutKeyStore()
                } else {
                    hideLayoutKeyStore()
                }
                return true

            }
        }
        return super.onOptionsItemSelected(item)
    }

}