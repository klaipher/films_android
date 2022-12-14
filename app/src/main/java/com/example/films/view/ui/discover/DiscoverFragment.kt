package com.example.films.view.ui.discover

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.fragment.findNavController
import com.example.films.R
import com.example.films.adapter.BaseAdapter
import com.example.films.databinding.EditTextLayoutBinding
import com.example.films.databinding.FragmentDiscoverBinding
import com.example.films.databinding.ListItemFilterBinding
import com.example.films.model.entities.Filter
import com.example.films.util.Sort
import com.example.films.util.getLong
import com.example.films.util.inflater
import com.example.films.view.ui.MainActivity
import com.example.films.view.viewmodel.DbFilterViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import com.google.android.material.transition.platform.MaterialElevationScale
import java.util.*

class DiscoverFragment : Fragment() {
    private var _binding: FragmentDiscoverBinding? = null
    private val binding: FragmentDiscoverBinding
        get() = _binding!!
    private val mRnd = Random()
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var adapter: BaseAdapter<Filter, ListItemFilterBinding>
    private val discoverViewModel: DiscoverViewModel by activityViewModels()
    private val dbFilterViewModel: DbFilterViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* slideIn = AnimationUtils.loadAnimation(context, R.anim.slide_in)
        slideOut = AnimationUtils.loadAnimation(context, R.anim.slide_out)*/
        adapter = BaseAdapter(
            expressionOnCreateViewHolder = { viewGroup ->
                viewGroup inflater ListItemFilterBinding::inflate
            },
            expressionViewHolderBinding = { filter, binding ->
                binding.apply {
                    setFilter(filter)
                    root.setOnClickListener {
                        val action =
                            DiscoverFragmentDirections.actionDiscoverFragmentToFilterFragment(filter)
                        findNavController().navigate(action)
                    }
                }

            }
        )
        setupMotionAnimations()
    }

    private fun setupMotionAnimations() {
        enterTransition = MaterialFadeThrough()
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
            interpolator = FastOutSlowInInterpolator()
            duration = resources.getLong(R.integer.material_motion_duration_long_2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateChipGroups()
        bottomNavigationView = (activity as MainActivity).binding.bottomNavigationView

        binding.apply {

            searchButton.setOnClickListener { _ ->
                findNavController().navigate(
                    R.id.action_discoverFragment_to_searchFragment
                )
            }

            //SortBy block
            expandSortBy.setOnClickListener {
                if (sortByChipGroup.isShown) {
                    sortByChipGroup.visibility = View.GONE
                } else {
                    sortByChipGroup.visibility = View.VISIBLE
                }
            }

            sortByChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                setupChipGroupAnim(group)
                if (checkedIds.isNotEmpty()) {
                    val ordinal = group.checkedChipId
                    val sortBy = Sort.values()[ordinal]
                    discoverViewModel.filterBuilder.value?.sortBy(sortBy)
                    binding.buttonGroup.visibility = View.VISIBLE
                } else {
                    binding.buttonGroup.visibility = View.GONE
                }
            }

            //Genres block
            expandGenres.setOnClickListener {
                if (genreChipGroup.isShown) {
                    genreChipGroup.visibility = View.GONE
                } else {
                    genreChipGroup.visibility = View.VISIBLE
                }
            }

            genreChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->

                setupChipGroupAnim(group)

                if (checkedIds.isNotEmpty()) {
                    buttonGroup.visibility = View.VISIBLE
                    discoverViewModel.filterBuilder.value?.withGenres(checkedIds)
                } else {
                    buttonGroup.visibility = View.INVISIBLE
                }
            }

            //Apply
            applyFilterBtn.setOnClickListener {
                val filter = discoverViewModel.filterBuilder.value!!.build()
                val action =
                    DiscoverFragmentDirections.actionDiscoverFragmentToFilterFragment(filter)
                findNavController().navigate(action)
            }

            //Clean
            cleanFilters.setOnClickListener {
                clearSelection()
            }

            //Save
            saveFilters.setOnClickListener {
                showEditTextDialog()
            }

            //Custom filters RecyclerView
            recyclerView.adapter = adapter
            recyclerView.setEmptyView(emptyView.root)
        }

        dbFilterViewModel.filters.observe(viewLifecycleOwner) { filters ->
            adapter.dataList = filters as MutableList<Filter>?
        }

    }

    private fun showEditTextDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val inflater = layoutInflater
        val dialogLayout = EditTextLayoutBinding.inflate(inflater)
        val editText = dialogLayout.editText
        with(builder) {
            setTitle("Filter's name")
            setPositiveButton("Create") { _, _ ->
                val name = editText.text.toString().trim()
                if (name.isNotEmpty()) {
                    discoverViewModel.filterBuilder.value?.name(name)
                    val filter = discoverViewModel.filterBuilder.value?.build()
                    if (filter != null) {
                        dbFilterViewModel.insertFilter(filter)
                        clearSelection()
                        Toast.makeText(
                            context,
                            "Filter ${filter.name} created!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            setView(dialogLayout.root)
            show()
        }


    }

    private fun clearSelection() {
        binding.genreChipGroup.clearCheck()
        binding.sortByChipGroup.clearCheck()
        discoverViewModel.initFilter()
    }


    private fun setupChipGroupAnim(group: ChipGroup) {
        val elevationScaleTransition = MaterialElevationScale(true).apply {
            interpolator = FastOutSlowInInterpolator()
        }
        TransitionManager.beginDelayedTransition(
            group.rootView as ViewGroup,
            elevationScaleTransition
        )
    }

    private fun setSortByBtn(chip: Chip, sort: Sort) {
        chip.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                discoverViewModel.filterBuilder.value?.sortBy(sort)
                binding.buttonGroup.visibility = View.VISIBLE
            } else {
                binding.buttonGroup.visibility = View.GONE
            }
        }
    }

    private fun populateChipGroups() {
        populateGenreChipGroup()
        populateSortByChipGroup()
    }

    private fun populateGenreChipGroup() {
        val chipGroup = binding.genreChipGroup
        val genreMap = discoverViewModel.genreMap.value
        chipGroup.removeAllViews()
        if (genreMap != null) {
            for ((key, value) in genreMap) {
                val genreChip = Chip(context)
                genreChip.id = key
                genreChip.isCheckable = true
                genreChip.text = value
                genreChip.setTextColor(Color.WHITE)
                genreChip.chipBackgroundColor = ColorStateList.valueOf(getRandomColor())
                chipGroup.addView(genreChip)
            }
        }
    }

    private fun populateSortByChipGroup() {
        val chipGroup = binding.sortByChipGroup
        val sortByMap = discoverViewModel.sortByMap.value
        chipGroup.removeAllViews()
        if (sortByMap != null) {
            for ((key, value) in sortByMap) {
                val sortByChip = Chip(context)
                sortByChip.isCheckable = true
                sortByChip.id = key.ordinal
                sortByChip.text = value
                sortByChip.setTextColor(Color.WHITE)
                sortByChip.chipBackgroundColor = ColorStateList.valueOf(getRandomColor())
                sortByChip.setOnClickListener {
                    setSortByBtn(sortByChip, key)
                }
                chipGroup.addView(sortByChip)
            }
        }
    }

    private fun getRandomColor(): Int {
        val baseColor = R.color.vermilion_100 //TODO maybe this color can be customizable
        val baseRed = Color.red(baseColor)
        val baseGreen = Color.green(baseColor)
        val baseBlue = Color.blue(baseColor)
        val red = (baseRed + mRnd.nextInt(256)) / 2
        val green = (baseGreen + mRnd.nextInt(256)) / 2
        val blue = (baseBlue + mRnd.nextInt(256)) / 2
        return Color.rgb(red, green, blue)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}