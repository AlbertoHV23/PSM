package com.psm.lmaddoubts.models

import android.app.Application
import com.psm.lmaddoubts.sqlite.DataDbHelper

class UserAplication: Application() {

    companion object{
        //este men hace que sea de manera global que cualquiera lo puededa utilizar
        lateinit var pref:prefs
        lateinit var dbHelper: DataDbHelper

    }
    override fun onCreate() {
        super.onCreate()
        pref = prefs(applicationContext)
        dbHelper =  DataDbHelper(applicationContext)

    }
}