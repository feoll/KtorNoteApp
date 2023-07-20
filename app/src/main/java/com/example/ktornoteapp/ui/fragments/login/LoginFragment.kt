package com.example.ktornoteapp.ui.fragments.login

import android.os.Bundle
import android.view.View
import com.example.ktornoteapp.databinding.FragmentLoginBinding
import com.example.ktornoteapp.ui.fragments.base.BaseFragment


class LoginFragment: BaseFragment<FragmentLoginBinding>() {
    override fun getViewBinding() = FragmentLoginBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}