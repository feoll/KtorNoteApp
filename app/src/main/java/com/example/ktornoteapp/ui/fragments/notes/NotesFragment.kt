package com.example.ktornoteapp.ui.fragments.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ktornoteapp.R
import com.example.ktornoteapp.databinding.FragmentNotesBinding
import com.example.ktornoteapp.ui.adapters.NoteAdapter
import com.example.ktornoteapp.ui.fragments.base.BaseFragment
import com.example.ktornoteapp.ui.viewmodels.NoteViewModel
import com.example.ktornoteapp.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class NotesFragment : BaseFragment<FragmentNotesBinding>(), MenuProvider {

    private val adapter by lazy { NoteAdapter() }
    private val viewModel by activityViewModels<NoteViewModel>()
    override fun getViewBinding() = FragmentNotesBinding.inflate(layoutInflater)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            this,
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
       menuInflater.inflate(R.menu.notes_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.profile -> {
                val direction = NotesFragmentDirections.actionNotesFragmentToProfileFragment()
                findNavController().navigate(direction)
            }
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createFab.setOnClickListener {
            val direction = NotesFragmentDirections.actionNotesFragmentToNoteDetailsFragment()
            findNavController().navigate(direction)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        lifecycleScope.launch {
            viewModel.allNoteState.collect { resource ->
                when(resource) {
                    is Resource.Success -> {
                        adapter.setList(resource.data)
                    }
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                }
            }
        }

        viewModel.getAllNotes()

    }

}