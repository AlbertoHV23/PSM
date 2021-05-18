package com.psm.lmaddoubts.Interface

import com.psm.lmaddoubts.models.tbl_usuario
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    //Servicios para consumir el Album
    @GET("Usuario/Usuarios")
    fun getUsuarios(): Call<List<tbl_usuario>>

    @Headers("Content-Type: application/json")
    @POST("Usuario/Exist")
    fun getUserLogeado(@Body albumData: tbl_usuario): Call<List<tbl_usuario>>


    @GET("Usuario/Usuarios/{id}")
    fun getUsuarioId(@Path("id") id: Int): Call<List<tbl_usuario>>

    @Headers("Content-Type: application/json")
    @POST("Usuario/Save")
    fun saveUsuarios(@Body albumData: tbl_usuario): Call<Int>
}