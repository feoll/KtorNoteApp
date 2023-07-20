package com.example.ktornoteapp.ui.fragments.notes

import android.os.Bundle
import android.view.View
import com.example.ktornoteapp.databinding.FragmentNotesBinding
import com.example.ktornoteapp.ui.fragments.base.BaseFragment


class NotesFragment : BaseFragment<FragmentNotesBinding>() {
    override fun getViewBinding() = FragmentNotesBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}