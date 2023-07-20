package com.example.ktornoteapp.ui.fragments.notedetails

import android.os.Bundle
import android.view.View
import com.example.ktornoteapp.databinding.FragmentNoteDetailsBinding
import com.example.ktornoteapp.ui.fragments.base.BaseFragment


class NoteDetailsFragment : BaseFragment<FragmentNoteDetailsBinding>() {
    override fun getViewBinding() = FragmentNoteDetailsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}