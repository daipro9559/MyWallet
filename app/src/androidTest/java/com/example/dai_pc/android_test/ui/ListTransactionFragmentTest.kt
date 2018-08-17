//package com.example.dai_pc.android_test.ui
//
//import android.arch.lifecycle.MutableLiveData
//import android.support.test.InstrumentationRegistry
//import android.support.test.espresso.Espresso
//import android.support.test.espresso.assertion.ViewAssertions
//import android.support.test.espresso.matcher.ViewMatchers
//import android.support.test.rule.ActivityTestRule
//import android.support.test.runner.AndroidJUnit4
//import com.example.dai_pc.android_test.R
//import com.android.example.github.testing.SingleFragmentActivity
//import com.example.dai_pc.android_test.entity.Resource
//import com.example.dai_pc.android_test.entity.Transaction
//import com.example.dai_pc.android_test.entity.loading
//import com.example.dai_pc.android_test.ultil.PreferenceHelper
//import com.example.dai_pc.android_test.ultil.ViewModelUtil
//import com.example.dai_pc.android_test.view.main.transactions.ListTransactionFragment
//import com.example.dai_pc.android_test.view.main.transactions.ListTransactionViewModel
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mockito.Mockito.`when`
//import org.mockito.Mockito.mock
//
//@RunWith(AndroidJUnit4::class)
//class ListTransactionFragmentTest {
//    @Rule
//    @JvmField
//    val activityRule = ActivityTestRule(SingleFragmentActivity::class.java, true, true)
//    private lateinit var viewModel: ListTransactionViewModel
//    private val fragment = ListTransactionFragment.newInstance()
//    private val listTransactionLiveData = MutableLiveData<Resource<List<Transaction>>>()
//
//    @Before
//    fun init() {
//        viewModel = mock(ListTransactionViewModel::class.java)
////        `when`(viewModel.listTransactionEther).thenReturn(listTransactionEther)
//        fragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
////        fragment.preferenceHelper = PreferenceHelper(InstrumentationRegistry.getContext())
//        activityRule.activity.addFragment(fragment)
//    }
//
//    @Test
//    fun testLoadListTransaction() {
////        listTransactionLiveData.postValue(loading())
////        viewModel.listTransactionLiveData.postValue(loading())
//        Espresso.onView(ViewMatchers.withId(R.id.progressBar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
//
//    }
//}