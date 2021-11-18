package ru.androidschool.intensiv.presentation.ui.feed

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_with_text.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.dto.movie.MoviesDto
import ru.androidschool.intensiv.data.vo.Movie

class MovieItem(
    private val content: Movie,
    private val onClick: (movie: Movie) -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_with_text

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.description.text = content.title
        viewHolder.movie_rating.rating = content.rating.toFloat()
        viewHolder.content.setOnClickListener {
            onClick.invoke(content)
        }

        // TODO Получать из модели
        Picasso.get()
            .load("https://image.tmdb.org/t/p/original${content.posterPath}")
            .into(viewHolder.image_preview)
    }
}
//https://api.themoviedb.org/3/g3jrVqSls1ItQvw6TXWMVcFS6FH.jpg