package com.example.ktornoteapp.ui.fragments.registration

import android.os.Bundle
import android.view.View
import com.example.ktornoteapp.databinding.FragmentRegistrationBinding
import com.example.ktornoteapp.ui.fragments.base.BaseFragment


class RegistrationFragment: BaseFragment<FragmentRegistrationBinding>(){
    override fun getViewBinding() = FragmentRegistrationBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}