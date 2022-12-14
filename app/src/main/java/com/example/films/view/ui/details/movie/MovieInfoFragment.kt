package com.example.films.view.ui.details.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.films.R
import com.example.films.adapter.MovieAdapter
import com.example.films.adapter.YoutubeVideoRecyclerViewAdapter
import com.example.films.databinding.FragmentMovieInfoBinding

class MovieInfoFragment : Fragment() {

    private var _binding: FragmentMovieInfoBinding? = null
    private val binding: FragmentMovieInfoBinding
        get() = _binding!!
    private lateinit var similarAdapter: MovieAdapter
    private lateinit var movieIntoCollectionAdapter: MovieAdapter
    private lateinit var videoAdapter: YoutubeVideoRecyclerViewAdapter
    private val viewModel: MovieDetailsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        similarAdapter = MovieAdapter()
        videoAdapter = YoutubeVideoRecyclerViewAdapter()
        movieIntoCollectionAdapter = MovieAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            recommendedRecyclerView.adapter = similarAdapter
            recommendedRecyclerView.setEmptyView(emptyViewRecommended.root)
            videosRecyclerView.adapter = videoAdapter

            collectionView.apply {
                moviesIntoCollection.adapter = movieIntoCollectionAdapter
                collectionCover.coverTitle.setOnClickListener {
                    //Remove collection cover
                    collectionCover.root.visibility = View.INVISIBLE

                }
                contentTitle.setOnClickListener {
                    //Add collection cover
                    collectionCover.root.visibility = View.VISIBLE
                }
            }


            fab.setOnClickListener {
                //Open bottomSheetFragment
                findNavController().navigate(R.id.action_movieDetailsFragment_to_bottomSheetFragment)
            }

            viewModel.selectedMovie.observe(viewLifecycleOwner) { selectedMovie ->
                movie = selectedMovie
            }
            viewModel.videos.observe(viewLifecycleOwner) { videos ->
                if (videos.isEmpty()) {
                    textSectionVideo.visibility = View.GONE
                    videosRecyclerView.visibility = View.GONE
                } else {
                    textSectionVideo.visibility = View.VISIBLE
                    videosRecyclerView.visibility = View.VISIBLE
                    videoAdapter.setDataList(videos)
                }
            }

        }

        viewModel.similarMovies.observe(viewLifecycleOwner) { similarMovies ->
            similarAdapter.addItems(similarMovies)
        }
        viewModel.moviesBelongsCollection.observe(viewLifecycleOwner) { collection ->
            movieIntoCollectionAdapter.addItems(collection.parts)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}