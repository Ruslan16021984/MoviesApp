package ru.androidschool.intensiv.data.db

import io.reactivex.Observable
import io.reactivex.Scheduler

abstract class CatchProvider<T>(val uiScheduler: Scheduler, val backgroundScheduler: Scheduler) {
    fun getObservable(type: RepositoryAccess): Observable<T> {
        return createObservable(type)
            .subscribeOn(backgroundScheduler).observeOn(uiScheduler)
    }

    private fun createObservable(type: RepositoryAccess): Observable<T> {
        when (type) {
            RepositoryAccess.OFFLINE -> {
                return createOfflineObservable()
            }
            RepositoryAccess.REMOTE_FIRST -> {
                return createRemoteObservable()
            }
            RepositoryAccess.OFFLINE_FIRST -> {
                val remoteObservable = createRemoteObservable()
                return createOfflineObservable()
                    .onErrorResumeNext(remoteObservable)
                    .switchIfEmpty(remoteObservable)
            }
            else -> {
                val remoteObservable = createRemoteObservable()
                return createOfflineObservable()
                    .onErrorResumeNext(remoteObservable)
                    .concatWith(remoteObservable)
            }
        }
    }

    protected abstract fun createRemoteObservable(): Observable<T>
    protected abstract fun createOfflineObservable(): Observable<T>
}