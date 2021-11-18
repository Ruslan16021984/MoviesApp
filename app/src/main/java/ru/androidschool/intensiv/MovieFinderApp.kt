package ru.androidschool.intensiv

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import ru.androidschool.intensiv.data.network.MovieApiInterface
import timber.log.Timber

class MovieFinderApp : Application() {

    var service: MovieApiInterface? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        initDebugTools()
    }
    private fun initDebugTools() {
        if (BuildConfig.DEBUG) {
            initTimber()
        }
    }
    @SuppressLint("MissingPermission")
    fun isNetworkConnection(): Boolean{
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork  = cm.activeNetworkInfo
        return activeNetwork !=null && activeNetwork.isConnectedOrConnecting
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        var instance: MovieFinderApp? = null
            private set
        fun hasNetwork(): Boolean{
            return instance?.isNetworkConnection()?: false
        }

    }
}
