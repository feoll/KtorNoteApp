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
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.ktornoteapp.R
import com.example.ktornoteapp.databinding.FragmentNotesBinding
import com.example.ktornoteapp.ui.fragments.base.BaseFragment


class NotesFragment : BaseFragment<FragmentNotesBinding>(), MenuProvider {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            R.id.search -> {}
        }
        return true
    }


}