package com.example.dai_pc.android_test.view.main.token

import android.os.Bundle
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.databinding.FragmentTokenBinding

class TokenFragment : BaseFragment<FragmentTokenBinding>(){


    companion object {
        fun newInstance() : TokenFragment{
            val  tokenFragment = TokenFragment()
            return tokenFragment
        }
    }
    override fun getlayoutId() = R.layout.fragment_token
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}