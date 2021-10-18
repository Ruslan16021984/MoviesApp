package ru.androidschool.intensiv.ui.watchlist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_watchlist.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.data.movie.MovieResponse

class WatchlistFragment : Fragment(R.layout.fragment_watchlist) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movies_recycler_view.layoutManager = GridLayoutManager(context, 4)
        movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }
        val getTopRatedMovies = MovieApiClient.apiClient.getTopRatedMovies("ru", 1)
        getTopRatedMovies.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                // Передаем результат в adapter и отображаем элементы
                val moviesList =
                    response.body()?.results?.map {
                        MoviePreviewItem(
                            it
                        ) { movie -> }
                    }?.toList()
                adapter.apply { moviesList?.let { addAll(it) } }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}")
            }

        })

        movies_recycler_view.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = WatchlistFragment()
    }
}
