package ru.androidschool.intensiv.ui.tvshows

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.tv_shows_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.tvshow.TvShow
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.data.tvshow.TvShowResponse


class TvShowsFragment : Fragment(R.layout.tv_shows_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getTopRatedMovies = MovieApiClient.apiClient.getTvShow("ru", 1)
            getTopRatedMovies.map { it.results }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progress_bar_show.visibility = View.VISIBLE }
            .doFinally { progress_bar_show.visibility = View.INVISIBLE }.subscribe({
                val showList = it.map  {TvShowItem(it)}.toList()
                adapter.apply { showList.let { addAll(it) } }
            }, {
                Log.e("TAG", "onFailure: ${it.message}")
            })
        tv_shows_recycler_view.adapter = adapter
    }



}
