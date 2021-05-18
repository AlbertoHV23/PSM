package com.psm.lmaddoubts.models

import android.app.Application

class sharedPreferences: Application() {

    companion object{
        //este men hace que sea de manera global que cualquiera lo puededa utilizar
        lateinit var pref:prefs
    }
    override fun onCreate() {
        super.onCreate()
        pref = prefs(applicationContext)

    }
}