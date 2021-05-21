package com.psm.lmaddoubts.models

import android.content.Context

class prefs(val context: Context) {
    val SHARED_NAME = "Mydb"
    val SHARED_ID = "id"
    val SHARED_NAMEU = "name"
    val SHARED_SURNAME = "surname"
    val SHARED_EMAIL = "email"
    val SHARED_PASSWORD = "password"
    val SHARED_vip = "vip"

    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun saveIdUsuario(name:String){
    storage.edit().putString(SHARED_ID,name).apply()
    }

    fun saveNombre(name:String){
        storage.edit().putString(SHARED_NAMEU,name).apply()
    }
    fun saveapellido(name:String){
        storage.edit().putString(SHARED_SURNAME,name).apply()
    }

    fun saveCorreo(name:String){
        storage.edit().putString(SHARED_EMAIL,name).apply()
    }
    fun savePasseord(name:String){
        storage.edit().putString(SHARED_PASSWORD,name).apply()
    }

    fun getIdUsuario(): String? {
        return storage.getString(SHARED_ID,"")!!
    }

    fun getNombre(): String? {
        return storage.getString(SHARED_NAMEU,"")!!
    }

    fun getApellido(): String? {
        return storage.getString(SHARED_SURNAME,"")!!
    }

    fun getEmail(): String? {
        return storage.getString(SHARED_EMAIL,"")!!
    }

    fun getPassword(): String? {
        return storage.getString(SHARED_PASSWORD,"")!!
    }


    fun wipe(){
        storage.edit().clear().apply()
    }
}