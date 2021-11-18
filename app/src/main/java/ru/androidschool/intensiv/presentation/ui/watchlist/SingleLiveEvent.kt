package ru.androidschool.intensiv.presentation.ui.watchlist

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w("TAG", "")
        }
        super.observe(owner, Observer { t ->
            if (pending.compareAndSet(true, false))
                observer.onChanged(t)
        })
    }

    override fun setValue(value: T) {
        pending.set(true)
        super.setValue(value)
    }
}