package ru.androidschool.intensiv.presentation.ui.watchlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_watchlist.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.db.TheMovieDatabase
import ru.androidschool.intensiv.data.dto.movieDetail.convertFromDetailToMovie

class WatchlistFragment : Fragment(R.layout.fragment_watchlist) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movies_recycler_view.layoutManager = GridLayoutManager(context, 4)
        movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }
        val db = TheMovieDatabase.getInstance(requireContext()).movieDetailDao()
        db.getMovieDetailGenres().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                val moviesList = it.map { MoviePreviewItem(
                    convertFromDetailToMovie(it)
                        ) { movie -> }
                    }.toList()
                adapter.apply { addAll(moviesList) }
            }, {})

        movies_recycler_view.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = WatchlistFragment()
    }
}
