package ru.androidschool.intensiv.presentation.ui.feed

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_tv_show.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.dto.movie.MoviesDto

class MovieSearchItem(
    private val content: MoviesDto,
    private val onClick: (movie: MoviesDto) -> Unit
): Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.description_tv.text = content.title
        viewHolder.movie_rating_tv.rating = content.rating
        Picasso.get()
            .load("https://image.tmdb.org/t/p/original${content.poster_path}")
            .into(viewHolder.image_preview_show)
    }

    override fun getLayout()= R.layout.item_tv_show
}