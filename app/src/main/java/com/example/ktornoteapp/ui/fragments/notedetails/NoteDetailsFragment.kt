package com.example.ktornoteapp.ui.fragments.notedetails

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ktornoteapp.data.models.params.CreateNoteParams
import com.example.ktornoteapp.databinding.FragmentNoteDetailsBinding
import com.example.ktornoteapp.ui.fragments.base.BaseFragment
import com.example.ktornoteapp.ui.viewmodels.NoteViewModel
import com.example.ktornoteapp.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class NoteDetailsFragment : BaseFragment<FragmentNoteDetailsBinding>() {

    private val viewModel by activityViewModels<NoteViewModel>()

    override fun getViewBinding() = FragmentNoteDetailsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeNoteEvents()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.saveBtn.setOnClickListener {
            val title = binding.titleEt.text.toString().trim()
            val description = binding.descriptionEt.text.toString().trim()
            viewModel.createNote(CreateNoteParams(title,description))
            findNavController().navigateUp()
        }
    }

    private fun subscribeNoteEvents() = lifecycleScope.launch {
        viewModel.noteState.collect { resource ->
            when(resource) {
                is Resource.Success -> {
                    Log.d("message", resource.data.toString())
                }
                is Resource.Error -> {
                    Log.d("message", resource.message.toString())
                }
                is Resource.Loading -> {}
            }
        }
    }
}