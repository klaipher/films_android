package com.example.films.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.films.NavGraphDirections
import com.example.films.databinding.ListItemMovieLongBinding
import com.example.films.databinding.ListItemMovieSmallBinding
import com.example.films.model.entities.Movie
import com.example.films.util.ViewSize
import com.example.films.util.inflater

class MovieAdapter : MultipleViewSizeAdapter<Movie>() {
    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }
    private val dataList = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return when (viewSize) {
            ViewSize.LONG -> MovieLongViewHolder(parent inflater ListItemMovieLongBinding::inflate)
            ViewSize.SMALL -> MovieSmallViewHolder(parent inflater ListItemMovieSmallBinding::inflate)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Movie>, position: Int) {
        holder.bind(dataList.currentList[position])
    }

    override fun getItemCount(): Int {
        return dataList.currentList.size
    }

    override fun addItems(items: List<Movie>) {
        dataList.submitList(items)
    }

    abstract inner class MovieViewHolder(
        binding: ViewDataBinding
    ) : BaseViewHolder<Movie>(binding) {

        abstract override fun bind(items: Movie)
        protected fun onClick(view: View, movie: Movie) {
            val action = NavGraphDirections.actionGlobalMovieDetailsFragment(movie)
            Navigation.findNavController(view).navigate(action)
        }
    }

    inner class MovieLongViewHolder(
        private val binding: ListItemMovieLongBinding,
    ) : MovieViewHolder(binding) {
        override fun bind(items: Movie) {
            binding.apply {
                movie = items
                executePendingBindings()
                root.setOnClickListener {
                    onClick(it, items)
                }
            }
        }
    }

    inner class MovieSmallViewHolder(
        private val binding: ListItemMovieSmallBinding,
    ) : MovieViewHolder(binding) {
        override fun bind(items: Movie) {
            binding.apply {
                movie = items
                executePendingBindings()
                root.setOnClickListener {
                    onClick(it, items)
                }
            }
        }
    }

}