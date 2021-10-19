package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.movie_details_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.movidetail.MovieCredits
import ru.androidschool.intensiv.data.movidetail.MovieDetail
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.feed.FeedFragment.Companion.KEY_ID

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val getArgs = arguments?.getInt(KEY_ID)

        val getMovieDetail = getArgs?.let { MovieApiClient.apiClient.getMovieDetail(it, "ru") }
        val castDetail = getArgs?.let { MovieApiClient.apiClient.getCastAndCrewForMovie(it, "ru") }
        getMovieDetail?.enqueue(object : Callback<MovieDetail> {
            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {

                val movies: MovieDetail? = response.body()
                tv_title.text = movies?.title ?: ""
                val genres = movies?.genres?.map { it.name }.toString()
                tv_genre.text = genres.substring(1, genres.length - 1)
                description.text = movies?.overview ?: ""
                movie_rating.rating = movies?.rating ?: 0f
                tv_year.text = movies?.release_date
                val companies = movies?.production_companies?.map { it.name }.toString()
                tv_studio.text = companies.substring(1, companies.length - 1)
                Picasso.get()
                    .load("https://image.tmdb.org/t/p/original${movies?.poster_path}")
                    .into(img_detail)
            }

            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}")
            }

        })
        castDetail?.enqueue(object : Callback<MovieCredits> {
            override fun onResponse(call: Call<MovieCredits>, response: Response<MovieCredits>) {
                val castList = response.body()?.cast?.map { CastItem(it) }?.toList()
                adapter.apply { castList?.let { addAll(it) } }
            }

            override fun onFailure(call: Call<MovieCredits>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}")
            }

        })

        items_container.adapter = adapter

    }
}

