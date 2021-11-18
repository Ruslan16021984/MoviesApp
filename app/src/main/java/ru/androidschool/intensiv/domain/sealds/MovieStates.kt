package ru.androidschool.intensiv.domain.sealds

import ru.androidschool.intensiv.data.db.DetailMovieAndGenres

sealed class MovieStates{
    object Loading: MovieStates()
    object CancelLoading: MovieStates()
    class Loaded(val data: List<DetailMovieAndGenres>): MovieStates()
    class Error(val message: String) : MovieStates()
}
