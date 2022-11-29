package com.example.mynoteapp.presentation.ui.fragments.note

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mynoteapp.R
import com.example.mynoteapp.databinding.FragmentNoteBinding
import com.example.mynoteapp.presentation.core.UIState
import com.example.mynoteapp.domain.model.Note
import com.example.mynoteapp.presentation.ui.fragments.note.adapter.NoteAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@SuppressLint("ResourceType")
@AndroidEntryPoint
class NoteFragment : Fragment(R.id.noteFragment) {

    private val noteAdapter by lazy { NoteAdapter(this::onItemLongClick) }
    private val viewModel by viewModels<NoteViewModel>()
    private val binding by viewBinding(FragmentNoteBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        initialize()
        setupRequest()
        setupClickListeners()
    }

    private fun initialize() = with(binding) {
        RecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        RecyclerView.adapter = noteAdapter
    }

    private fun setupRequest() {
        viewModel.getAllNotes()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.editNoteState.collect { state ->
                    when (state) {
                        is UIState.Loading -> {

                        }
                        is UIState.Error -> {

                        }
                        is UIState.Success -> {

                        }
                        is UIState.Empty -> {

                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAllNotesState.collect { state ->
                    when (state) {
                        is UIState.Loading -> {

                        }
                        is UIState.Error -> {

                        }
                        is UIState.Success -> {
                            noteAdapter.setData(state.data)
                        }
                        is UIState.Empty -> {

                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.deleteNoteState.collect { state ->
                    when (state) {
                        is UIState.Success -> {
                            viewModel.getAllNotes()
                        }
                        is UIState.Loading -> {

                        }
                        is UIState.Error -> {

                        }
                        is UIState.Empty -> {

                        }
                    }
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnFab.setOnClickListener {
            findNavController().navigate(R.id.addNoteFragment)
        }
    }

    private fun onItemLongClick(note: Note) {
        viewModel.deleteNote(note)
    }
}