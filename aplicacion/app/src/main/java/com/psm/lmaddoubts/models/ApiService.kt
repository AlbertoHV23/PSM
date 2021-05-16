package com.psm.lmaddoubts.models

import retrofit2.Call
import com.psm.lmaddoubts.models.tbl_usuarios
import retrofit2.http.*

//Retrofi usa una interface para hacer la petición hacia el servidor
interface ApiService {
    //Servicios para consumir el Album
  //  @GET("Album/Albums")
    //fun getAlbums():Call<List<Album>>

    //@GET("Album/Albums/{id}")
    //fun getAlbum(@Path("id") id: Int): Call<List<Album>>

    //@Headers("Content-Type: application/json")
    //@POST("Album/Save")
    //fun saveAlbum(@Body albumData: Album):Call<Int>

    //Servicios para consumir el USUARIOS
    //TODOS LOS USUARIOS:
    @GET("Usuario/Usuarios")
    fun getAlbums():Call<List<tbl_usuarios>>
    // 1 USUARIOS
    @GET("Usuario/Usuarios/{id}")
    fun getAlbum(@Path("id") id: Int): Call<List<tbl_usuarios>>
    //NUEVO OBJETO
    @Headers("Content-Type: application/json")
    @POST("Usuario/Save")
    fun saveAlbum(@Body userData: tbl_usuarios):Call<Int>


}