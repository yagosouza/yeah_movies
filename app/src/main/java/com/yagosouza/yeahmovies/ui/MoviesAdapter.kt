package com.yagosouza.yeahmovies.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yagosouza.yeahmovies.AppConstants
import com.yagosouza.yeahmovies.R
import com.yagosouza.yeahmovies.network.model.dto.MovieDTO
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MoviesAdapter (val context: Context, val movies: List<MovieDTO>) :
    RecyclerView.Adapter<MoviesAdapter.MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_movie, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        movies.elementAt(position).apply {
            Picasso.get()
                .load("${AppConstants.TMDB_IMAGE_BASE_URL_W185}${poster_path}")
                //.placeholder(R.color.gray)
                .into(holder.poster)
        }
    }

    override fun getItemCount(): Int = movies.size

    class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardView: CardView = itemView.cv_movie
        var poster: ImageView = itemView.iv_movie_poster
    }
}
