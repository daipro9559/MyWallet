package com.example.dai_pc.android_test.view.transaction

import android.content.Context
import com.example.dai_pc.android_test.entity.TransactionSendObject
import com.example.dai_pc.android_test.repository.WalletRepository
import com.example.dai_pc.android_test.service.AccountService
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SendTransactionPresenterImp
@Inject constructor(private val preferenceHelper: PreferenceHelper,
                    private val context: Context,
                    private val accountService: AccountService,
                    private val walletRepository: WalletRepository) : SendTransactionPresenter<SendTransactionFragment> {


    private var view: SendTransactionView? = null
    override fun bindView(v: SendTransactionFragment) {
        this.view = v
    }

    override fun dropView() {
        this.view = null
    }

    private var passIsValidated = false

    private val ADDRESS_REGEX = "0x\\w"

    private lateinit var transactionSendObject: TransactionSendObject


    override fun validateData(transactionSendObject: TransactionSendObject, isRequireCheckPass: Boolean) {
        this.transactionSendObject = transactionSendObject
        if (isRequireCheckPass) {
            validatePassword()
        } else {
            view!!.sendTransaction(transactionSendObject)
        }

    }

    private fun validatePassword() {
        accountService.getPassword(context, walletRepository.accountSelected.value!!)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (transactionSendObject.pass!!.contentEquals(it)) {
                        view!!.sendTransaction(transactionSendObject)
                    } else {
                        view!!.errorInput("Password is not correct!")
                    }
                }, {}
                )

    }

}