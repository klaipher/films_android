package com.example.films.view.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.films.R
import com.example.films.adapter.SectionRecyclerViewAdapter
import com.example.films.databinding.FragmentHomeBinding
import com.example.films.model.entities.Movie
import com.example.films.model.entities.Person
import com.example.films.model.entities.Section
import com.example.films.util.ViewSize
import com.example.films.view.ui.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
     val binding : FragmentHomeBinding
        get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var sectionAdapter: SectionRecyclerViewAdapter
    private lateinit var bottomNavigationView: BottomNavigationView

    // Sections
    private val upcomingSection: Section<Movie> =
        Section("Upcoming", null, Movie::class.java, null, ViewSize.SMALL)
    private val topRatedSection: Section<Movie> =
        Section("Top Rated", null, Movie::class.java, null, ViewSize.SMALL)
    private var trendingSection: Section<Movie> =
        Section("Trending this week", "Movies", Movie::class.java, null, ViewSize.SMALL)
    private var trendingPerson: Section<Person> =
        Section("Trending this week", "Actors", Person::class.java, null, ViewSize.SMALL)
    private var sectionList: List<Section<*>> =
        listOf(upcomingSection, topRatedSection, trendingSection, trendingPerson)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sectionAdapter = SectionRecyclerViewAdapter(this)
        setupTransitions()
    }

    private fun setupTransitions() {
        val elevationScaleTransition = MaterialElevationScale(true)
            .setInterpolator(FastOutSlowInInterpolator())
        enterTransition = elevationScaleTransition
        reenterTransition = elevationScaleTransition
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        setupWithNavController(binding.toolbar, findNavController(), appBarConfiguration)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        bottomNavigationView = (activity as MainActivity).binding.bottomNavigationView
        setupListeners()
        setupRecyclerView()
    }

    private fun setupListeners() = binding.run {
        imageProfile.setOnClickListener {
            Toast.makeText(context, "Soon", Toast.LENGTH_SHORT).show()
        }

        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.searchFragment)
                findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
            true
        }
    }

    private fun setupRecyclerView() = binding.sectionRv.run {
        adapter = sectionAdapter
        initSectionedRecyclerView()
    }

    private fun initSectionedRecyclerView() {
        sectionAdapter.addItems(sectionList)
        upcomingSection.liveData = viewModel.upcomingMovies
        topRatedSection.liveData = viewModel.topRatedMovies
        trendingSection.liveData = viewModel.trendingMovies
        trendingPerson.liveData = viewModel.trendingPerson
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}