package com.smorzhok.servicestest

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log

class MyIntentService2 : IntentService(NAME) {

    override fun onHandleIntent(intent: Intent?) {
        log("onStartCommand")
        val page = intent?.getIntExtra(PAGE, 0) ?: 0
        for (i in 0..5) {
            Thread.sleep(1000)
            log("Timer $i $page")
        }
    }

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        setIntentRedelivery(true)
    }


    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyIntentService2: $message")
    }

    companion object {
        private const val PAGE = "page"
        private const val NAME = "MyIntentService2"
        fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyIntentService2::class.java).apply {
                putExtra(PAGE, page)
            }
        }
    }
}