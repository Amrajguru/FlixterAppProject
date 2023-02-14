package com.example.flixterapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flixterapp.R.id
/**
 * [RecyclerView.Adapter] that can display a [Movies] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class PopularMovieRecyclerViewAdapter(
    private val movies: List<Movies>,
    private val mListener: OnListFragmentInteractionListener?
    )
    : RecyclerView.Adapter<PopularMovieRecyclerViewAdapter.MovieViewHolder>()
    {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_most_popular_movies, parent, false)
        return MovieViewHolder(view)
    }

    /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mMovies: Movies? = null
        val mMovieTitle: TextView = mView.findViewById<View>(id.movie_title) as TextView
        val mMovieOverview: TextView = mView.findViewById<View>(id.movie_overview) as TextView
        val mMovieImage: ImageView = mView.findViewById<View>(id.movie_image) as ImageView

        override fun toString(): String {
            return mMovies.toString() + " '" + mMovieTitle.text + "'" + mMovieOverview.text
        }
    }

    /**
     * This lets us "bind" each Views in the ViewHolder to its' actual data!
     */
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.mMovies = movie
        holder.mMovieTitle.text = movie.title
        holder.mMovieOverview.text = movie.overview
        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500"+movie.imageUrlhalf)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.mMovieImage)

        holder.mView.setOnClickListener {
            holder.mMovies?.let { movie ->
                mListener?.onItemClick(movie)
            }
        }
    }

    /**
     * Remember: RecyclerView adapters require a getItemCount() method.
     */
     override fun getItemCount(): Int {
        return movies.size
    }
}