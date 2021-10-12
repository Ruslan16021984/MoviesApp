package ru.androidschool.intensiv.data

object MockRepository {

    fun getMovies(): List<Movie> {

        val moviesList = mutableListOf<Movie>()
        for (x in 0..10) {
            val movie = Movie(
                title = "Spider-Man $x",
                voteAverage = 10.0 - x
            )
            moviesList.add(movie)
        }

        return moviesList
    }
    fun getTvShow(): List<TvShow>{
        val tvShowList = mutableListOf<TvShow>()
        for (x in 0..10) {
            val show = TvShow(
                title = "Spider-Women $x",
                voteAverage = 10.0 - x
            )
            tvShowList.add(show)
        }

        return tvShowList
    }
}
