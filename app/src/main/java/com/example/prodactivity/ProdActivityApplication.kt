package com.example.prodactivity

import android.app.Application
import com.example.prodactivity.data.database.AppContainer
import com.example.prodactivity.data.database.AppDataContainer

class ProdActivityApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
