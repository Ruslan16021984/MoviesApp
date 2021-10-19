package ru.androidschool.intensiv.ui.movie_details

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_actor.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.movidetail.Cast

class CastItem(
    private val content: Cast
) : Item() {

    override fun getLayout() = R.layout.item_actor

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.tv_name.text = content.name
        Picasso.get()
            .load("https://image.tmdb.org/t/p/original${content.profile_path}")
            .into(viewHolder.image_icon)
    }

}