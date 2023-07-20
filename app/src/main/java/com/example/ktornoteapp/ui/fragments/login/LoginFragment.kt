package com.example.ktornoteapp.ui.fragments.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ktornoteapp.data.models.params.LoginParams
import com.example.ktornoteapp.databinding.FragmentLoginBinding
import com.example.ktornoteapp.ui.fragments.base.BaseFragment
import com.example.ktornoteapp.ui.viewmodels.AuthViewModel
import com.example.ktornoteapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding>() {

    private val viewModel by activityViewModels<AuthViewModel>()
    override fun getViewBinding() = FragmentLoginBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToLoginEvents()
        setupListeners()
    }

    private fun setupListeners() {
        binding.loginBtn.setOnClickListener {
            val email = binding.emailEt.text.trim().toString()
            val password = binding.passwordEt.text.trim().toString()
            viewModel.login(LoginParams(
                email = email,
                password = password
            ))
        }
    }

    private fun subscribeToLoginEvents() = lifecycleScope.launch {
        viewModel.loginState.collect { resource ->
            when(resource) {
                is Resource.Success -> {
                    hideProgressBar()
                    val direction = LoginFragmentDirections.actionLoginFragmentToNotesFragment()
                    findNavController().navigate(direction)
                }
                is Resource.Error -> {
                    Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
                    hideProgressBar()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.progressBar.isVisible = true
    }
    private fun hideProgressBar() {
        binding.progressBar.isVisible = false
    }
}