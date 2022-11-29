package com.example.mynoteapp.presentation.ui.fragments.add

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
import com.example.mynoteapp.databinding.FragmentAddNoteBinding
import com.example.mynoteapp.presentation.core.UIState
import com.example.mynoteapp.domain.model.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@SuppressLint("ResourceType")
@AndroidEntryPoint
class AddNoteFragment : Fragment(R.id.addNoteFragment) {

    private val binding by viewBinding(FragmentAddNoteBinding::bind)
    private val viewModel by viewModels<AddNoteViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupRequest()
        setupObservers()
        setupClickListeners()
    }

    private fun initialize() {
    }

    private fun setupRequest() {
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addNoteState.collect { state ->
                    when (state) {
                        is UIState.Loading -> {
                        }
                        is UIState.Success -> {
                            setupClickListeners()
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
        fun setupClickListeners() = with(binding) {
            btnSave.setOnClickListener {
                viewModel.addNote(
                    Note(
                        title = editTitle.text.toString(),
                        description = editDesciption.text.toString(),
                        createdAt = System.currentTimeMillis()
                    )
                )
                findNavController().navigateUp()
            }
        }
    }
}