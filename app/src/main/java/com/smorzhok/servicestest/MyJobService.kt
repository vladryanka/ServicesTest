package com.smorzhok.servicestest

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyJobService : JobService() {
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartJob")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            scope.launch {
                var workItem = params?.dequeueWork()
                while (workItem!= null) {
                    val page = workItem.intent.getIntExtra(PAGE,0)
                    for (i in 0..100) {
                        delay(1000)
                        log("Timer $i $page")
                    }
                    params?.completeWork(workItem)
                    workItem = params?.dequeueWork()
                }
            }
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        log("onStopJob")
        return true
    }

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        scope.cancel()
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyJobService: $message")
    }

    companion object {
        const val JOB_ID = 1
        private const val PAGE = "page"
        fun getIntent(context: Context, page: Int): Intent {
            return Intent(context, MyJobService::class.java).apply {
                putExtra(PAGE, page)
            }
        }
    }

}