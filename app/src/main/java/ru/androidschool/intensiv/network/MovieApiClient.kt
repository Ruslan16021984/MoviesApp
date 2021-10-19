package ru.androidschool.intensiv.network

import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.androidschool.intensiv.BuildConfig.THE_MOVIE_DATABASE_API
import ru.androidschool.intensiv.data.api.logger.CustomHttpLogging
import java.util.concurrent.TimeUnit

object MovieApiClient {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private val requestInterceptor = Interceptor{chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", THE_MOVIE_DATABASE_API)
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return@Interceptor chain.proceed(request)

    }
   private val builder = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .connectionPool(ConnectionPool(5, 30, TimeUnit.SECONDS))
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
       .addInterceptor(HttpLoggingInterceptor(CustomHttpLogging()).apply {
           this.level = HttpLoggingInterceptor.Level.BODY
       })
    val apiClient: MovieApiInterface by lazy {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return@lazy retrofit.create(MovieApiInterface::class.java)
    }

}