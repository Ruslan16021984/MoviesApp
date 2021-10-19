package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.tv_shows_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.data.tvshow.TvShowResponse


class TvShowsFragment : Fragment(R.layout.tv_shows_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getTopRatedMovies = MovieApiClient.apiClient.getTvShow("ru", 1)
        getTopRatedMovies.enqueue(object : Callback<TvShowResponse>{
            override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                val showList = response.body()?.results?.map  { TvShowItem(it) }?.toList()
                adapter.apply { showList?.let { addAll(it) } }
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}")
            }

        })
        tv_shows_recycler_view.adapter = adapter
    }



}
