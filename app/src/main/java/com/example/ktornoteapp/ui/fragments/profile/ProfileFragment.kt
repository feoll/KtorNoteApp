package com.example.ktornoteapp.ui.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ktornoteapp.R
import com.example.ktornoteapp.databinding.FragmentProfileBinding
import com.example.ktornoteapp.ui.fragments.base.BaseFragment


class ProfileFragment: BaseFragment<FragmentProfileBinding>() {
    override fun getViewBinding() = FragmentProfileBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}