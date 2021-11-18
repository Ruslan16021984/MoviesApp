package ru.androidschool.intensiv.data.dto.movieDetail

import ru.androidschool.intensiv.data.db.DetailMovieAndGenres
import ru.androidschool.intensiv.data.db.GenreDb
import ru.androidschool.intensiv.data.db.MovieDetailDb
import ru.androidschool.intensiv.data.dto.movie.MoviesDto
import ru.androidschool.intensiv.data.vo.Movie

fun convertFromMovieToDetail(dto: MovieDetailDto): MovieDetailDb {
    return MovieDetailDb(
        id = dto.id,
        title = dto.title, adult = dto.adult,
        backdrop_path = dto.backdrop_path,
        original_language = dto.original_language,
        original_title = dto.original_title,
        overview = dto.overview,
        popularity = dto.popularity,
        poster_path = dto.poster_path,
        release_date = dto.release_date,
        video = dto.video,
        vote_average = dto.vote_average,
        vote_count = dto.vote_count,
        budget = dto.budget,
        homepage = dto.homepage,
        imdb_id = dto.imdb_id,
        revenue = dto.revenue,
        runtime = dto.runtime,
        status = dto.status,
        tagline = dto.tagline
    )
}

fun convertFromDetailToMovie(details: DetailMovieAndGenres): Movie {
    return Movie(
        id = details.movieDetail.id,
        title = details.movieDetail.title,
        popularity = details.movieDetail.popularity,
        overview = details.movieDetail.overview,
        voteAverage = details.movieDetail.vote_average,
        releaseDate = details.movieDetail.release_date,
        posterPath = details.movieDetail.poster_path,
        originalTitle = details.movieDetail.original_title
    )
}

fun convertFromGenresTo(ownerId: Int, ganres: List<GenreDto>): List<GenreDb> {
    return ganres.map {
        GenreDb(
            id = it.id,
            name = it.name,
            ownerId = ownerId
        )
    }
}