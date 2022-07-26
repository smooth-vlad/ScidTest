package com.android.scidtest.di

import android.app.Application
import android.content.Context
import com.android.scidtest.di.main_component.DaggerMainComponent
import com.android.scidtest.di.main_component.MainComponent

class App : Application() {

    private lateinit var _mainComponent: MainComponent

    companion object {
        fun getMainComponent(context: Context): MainComponent =
            (context.applicationContext as App)._mainComponent
    }

    override fun onCreate() {
        super.onCreate()

        _mainComponent = DaggerMainComponent.factory().create(applicationContext)
    }
}