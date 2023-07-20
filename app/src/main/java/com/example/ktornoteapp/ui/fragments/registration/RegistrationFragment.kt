package com.example.ktornoteapp.ui.fragments.registration

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ktornoteapp.data.models.params.LoginParams
import com.example.ktornoteapp.data.models.params.RegistrationParams
import com.example.ktornoteapp.databinding.FragmentRegistrationBinding
import com.example.ktornoteapp.ui.fragments.base.BaseFragment
import com.example.ktornoteapp.ui.fragments.login.LoginFragmentDirections
import com.example.ktornoteapp.ui.viewmodels.AuthViewModel
import com.example.ktornoteapp.utils.Resource
import kotlinx.coroutines.launch


class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>() {

    private val viewModel by activityViewModels<AuthViewModel>()
    override fun getViewBinding() = FragmentRegistrationBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToRegistrationEvents()
        setupListeners()
    }

    private fun setupListeners() {
        binding.registrationBtn.setOnClickListener {
            val email = binding.emailEt.text.trim().toString()
            val password = binding.passwordEt.text.trim().toString()
            val repeatPassword = binding.repeatPasswordEt.text.trim().toString()
            viewModel.registration(
                registrationParams = RegistrationParams(
                    email = email,
                    password = password
                ),
                repeatPassword = repeatPassword
            )
        }
    }

    private fun subscribeToRegistrationEvents() = lifecycleScope.launch {
        viewModel.registrationState.collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    hideProgressBar()
                    val direction = RegistrationFragmentDirections.actionRegistrationFragmentToNotesFragment()
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