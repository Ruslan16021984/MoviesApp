package ru.androidschool.intensiv.data.mapper

import ru.androidschool.intensiv.data.dto.movie.MovieResponseDto
import ru.androidschool.intensiv.data.dto.movie.MoviesDto
import ru.androidschool.intensiv.data.vo.Movie

object MovieMapper {
    fun toValueObject(dto: MovieResponseDto):List<Movie>{
        return dto.results.map { toValueObject(it) }
    }
    private fun toValueObject(dto: MoviesDto): Movie{
        return Movie(
            id = dto.id,
            originalTitle = dto.original_title,
            overview = dto.overview,
            popularity = dto.popularity,
            posterPath = dto.poster_path,
            releaseDate = dto.release_date,
            title = dto.title,
            voteAverage = dto.vote_average
        )
    }

}