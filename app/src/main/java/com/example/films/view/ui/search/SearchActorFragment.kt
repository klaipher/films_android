package com.example.films.view.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.films.adapter.PersonAdapter
import com.example.films.databinding.FragmentSearchActorBinding
import com.example.films.util.ViewSize

class SearchActorFragment : Fragment() {
    private var _binding: FragmentSearchActorBinding? = null
    private val binding: FragmentSearchActorBinding
        get() = _binding!!
    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var adapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = PersonAdapter()
        adapter.viewSize = ViewSize.LONG
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchActorBinding.inflate(inflater, container, false)
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.setEmptyView(emptyView.root)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.queriedActors.observe(viewLifecycleOwner) { persons ->
            adapter.addItems(persons)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}