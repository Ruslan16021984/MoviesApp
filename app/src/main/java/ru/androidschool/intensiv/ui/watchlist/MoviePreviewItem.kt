package ru.androidschool.intensiv.ui.watchlist

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_with_text.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.movie.Movies

class MoviePreviewItem(
    private val content: Movies,
    private val onClick: (movie: Movies) -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_small

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.image_preview.setOnClickListener {
            onClick.invoke(content)
        }
        viewHolder.description.text = content.original_title
        // TODO Получать из модели
        Picasso.get()
            .load("https://image.tmdb.org/t/p/original${content.poster_path}")
            .into(viewHolder.image_preview)
    }
}
