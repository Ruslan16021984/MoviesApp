package ru.androidschool.intensiv.presentation.ui.watchlist

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.androidschool.intensiv.data.db.MovieDetailDao
import ru.androidschool.intensiv.domain.sealds.MovieStates

class WatchViewModel : ViewModel() {

    private val _movieDetailGenres = MutableLiveData<MovieStates>()
    val movieDetailGenres: LiveData<MovieStates> get() = _movieDetailGenres

    @SuppressLint("CheckResult")
    fun getMyFavoriteMovies(db: MovieDetailDao) {
        db.getMovieDetailGenres().subscribeOn(Schedulers.io())
            .doOnSubscribe {
            _movieDetailGenres.postValue(MovieStates.Loading)
        }
            .doFinally {
                _movieDetailGenres.postValue(MovieStates.CancelLoading)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _movieDetailGenres.postValue(MovieStates.Loaded(it))
            }, {
                _movieDetailGenres.postValue(MovieStates.Error(it.message.toString()))
            })
    }
}