package com.example.films.view.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.films.R
import com.example.films.adapter.ViewPagerAdapter
import com.example.films.databinding.FragmentSavedBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialElevationScale


class SavedFragment : Fragment() {
    private var _binding: FragmentSavedBinding? = null
    private val binding: FragmentSavedBinding
        get() = _binding!!
    private lateinit var toSeeFragment: ToSeeFragment
    private lateinit var seenFragment: SeenFragment
    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var mGridLayoutManager: GridLayoutManager? = null
    private var layoutGrid = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toSeeFragment = ToSeeFragment()
        seenFragment = SeenFragment()
        mLinearLayoutManager = LinearLayoutManager(context)
        mGridLayoutManager = GridLayoutManager(context, 3)
        setupMotionAnimations()
    }

    private fun setupMotionAnimations() {
        enterTransition = MaterialElevationScale(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateToolbar()

        // Listen menu item click and change layout into recyclerview
        // owned by SearchActor & SearchMovie fragment
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_switch_grid -> {
                    switchLayout(mGridLayoutManager)
                    updateToolbar()
                }
                R.id.menu_switch_list -> {
                    switchLayout(mLinearLayoutManager)
                    updateToolbar()
                }
            }
            false
        }
        setupTabLayout()
    }

    private fun setupTabLayout() {
        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        viewPagerAdapter.addFragment(toSeeFragment)
        viewPagerAdapter.addFragment(seenFragment)
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "To See"
                1 -> tab.text = "Seen"
            }
        }.attach()
    }

    //Change menu icon in toolbar showing list or grid view
    private fun updateToolbar() {
        layoutGrid = !layoutGrid
        val gridView = binding.toolbar.menu.findItem(R.id.menu_switch_grid)
        gridView.isVisible = layoutGrid
        val listView = binding.toolbar.menu.findItem(R.id.menu_switch_list)
        listView.isVisible = !layoutGrid
    }

    private fun switchLayout(layoutManager: RecyclerView.LayoutManager?) {
        Toast.makeText(context, "Soon!", Toast.LENGTH_SHORT).show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}