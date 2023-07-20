package com.example.ktornoteapp.ui.fragments.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ktornoteapp.databinding.FragmentProfileBinding
import com.example.ktornoteapp.ui.fragments.base.BaseFragment
import com.example.ktornoteapp.ui.fragments.login.LoginFragmentDirections
import com.example.ktornoteapp.ui.viewmodels.AuthViewModel
import com.example.ktornoteapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel by activityViewModels<AuthViewModel>()

    override fun getViewBinding() = FragmentProfileBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToProfileEvents()
        viewModel.getToken()
        setupListeners()
    }

    private fun setupListeners() {
        binding.loginBtn.setOnClickListener {
            val direction = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
            findNavController().navigate(direction)
        }
        binding.registrationBtn.setOnClickListener {
            val direction = ProfileFragmentDirections.actionProfileFragmentToRegistrationFragment()
            findNavController().navigate(direction)
        }
        binding.logoutBtn.setOnClickListener {
            viewModel.logout()
            findNavController().navigateUp()
        }
    }

    private fun subscribeToProfileEvents() = lifecycleScope.launch {
        viewModel.currentAuthState.collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.loginBtn.isVisible = false
                    binding.registrationBtn.isVisible = false
                    binding.logoutBtn.isVisible = true
                }

                is Resource.Error -> {
                    binding.logoutBtn.isVisible = false
                    binding.loginBtn.isVisible = true
                    binding.registrationBtn.isVisible = true
                }

                is Resource.Loading -> Unit
            }
        }
    }
}