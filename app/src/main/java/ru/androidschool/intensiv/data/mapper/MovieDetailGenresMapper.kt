package ru.androidschool.intensiv.data.dto.movieDetail

import ru.androidschool.intensiv.data.db.DetailMovieAndGenres
import ru.androidschool.intensiv.data.db.GenreDb
import ru.androidschool.intensiv.data.db.MovieDetailDb
import ru.androidschool.intensiv.data.dto.movie.MoviesDto

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

fun convertFromDetailToMovie(details: DetailMovieAndGenres): MoviesDto {
    return MoviesDto(
        id = details.movieDetail.id,
        video = details.movieDetail.video,
        title = details.movieDetail.title,
        popularity = details.movieDetail.popularity,
    overview = details.movieDetail.overview,
    genre_ids = details.genre.map { it.id },
    adult = details.movieDetail.adult,
    backdrop_path = details.movieDetail.backdrop_path,
    original_language = details.movieDetail.original_language,
    original_title = details.movieDetail.original_title,
    poster_path = details.movieDetail.poster_path,
    release_date = details.movieDetail.release_date,
    vote_average = details.movieDetail.vote_average,
    vote_count = details.movieDetail.vote_count)
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