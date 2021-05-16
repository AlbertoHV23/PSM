package com.psm.lmaddoubts
import com.psm.lmaddoubts.models.tbl_usuario
import retrofit2.Call
import retrofit2.http.*

//Retrofi usa una interface para hacer la petición hacia el servidor
interface Service{

    //Servicios para consumir el Album
    @GET("Usuario/Usuarios")
    fun getAlbums():Call<List<tbl_usuario>>

    @GET("Usuario/Usuarios/{id}")
    fun getAlbum(@Path("id") id: Int): Call<List<tbl_usuario>>

    @Headers("Content-Type: application/json")
    @POST("Usuario/Save")
    fun saveAlbum(@Body albumData: tbl_usuario):Call<Int>

}