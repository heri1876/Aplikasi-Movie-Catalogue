package com.shadow.moviecatalogue.repository.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProvider private constructor() : BaseSchedulerProvider {

    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    companion object {

        private var instance: SchedulerProvider? = null

        @Synchronized
        fun getInstance() : SchedulerProvider {
            if (instance == null) {
                instance = SchedulerProvider()
            }
            return instance!!
        }
    }

}