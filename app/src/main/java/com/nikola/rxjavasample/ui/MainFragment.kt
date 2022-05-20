package com.nikola.rxjavasample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikola.rxjavasample.ui.adapter.MainNotesAdapter
import com.nikola.rxjavasample.data.model.ResultWrapper
import com.nikola.rxjavasample.ui.viewmodel.MainViewModel
import com.nikola.rxjavasample.databinding.FragmentMainLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainLayoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapterNotes: MainNotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterNotes = MainNotesAdapter()
        getNotes()
        setUpView()
    }


    private fun setUpView() {
        binding.apply {
            notesRecyclerView.apply {
                adapter = adapterNotes
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
    }

    private fun getNotes() {
        val progressDialog = binding.notesProgressBar

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.notesFlow.collectLatest {result->
                when(result){
                    is ResultWrapper.Success ->{
                        progressDialog.visibility = View.INVISIBLE
                        adapterNotes.submitList(result.value.notesList)

                    }
                    is ResultWrapper.GenericError ->{
                        Toast.makeText(requireContext(), result.error?.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}