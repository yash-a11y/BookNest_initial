package org.ycode.book_nest

import android.app.Application
import org.ycode.book_nest.di.initKoin
import org.koin.android.ext.koin.androidContext

class bookNestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@bookNestApp)
        }
    }
}