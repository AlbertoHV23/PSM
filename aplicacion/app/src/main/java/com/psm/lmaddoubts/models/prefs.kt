package com.psm.lmaddoubts.models

import android.content.Context

class prefs(val context: Context) {
    val SHARED_NAME = "Mydb"
    val SHARED_USERNAME = "username"
    val SHARED_vip = "vip"

    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun saveName(name:String){
    storage.edit().putString(SHARED_USERNAME,name).apply()
    }

    fun saveVip(vip:Boolean){
        storage.edit().putBoolean(SHARED_vip,vip).apply()
    }
    fun getName(): String? {
        return storage.getString(SHARED_USERNAME,"")!!
    }
    fun getVip():Boolean{
    return storage.getBoolean(SHARED_vip,false  )
    }

    fun wipe(){
        storage.edit().clear().apply()
    }
}