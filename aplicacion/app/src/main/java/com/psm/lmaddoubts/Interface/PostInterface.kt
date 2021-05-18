package com.psm.lmaddoubts.Interface

import com.psm.lmaddoubts.models.tbl_post
import com.psm.lmaddoubts.models.tbl_publicaciones
import com.psm.lmaddoubts.models.tbl_usuario
import retrofit2.Call
import retrofit2.http.*

interface PostInterface {
    //Servicios para consumir el Album
    @GET("Post/Posts")
    fun getPosts(): Call<List<tbl_post>>

    //Servicios para consumir el Album
    @GET("Post/PostsCN")
    fun getPublicaciones(): Call<List<tbl_publicaciones>>

    @Headers("Content-Type: application/json")
    @POST("Post/Save")
    fun saveUsuarios(@Body albumData: tbl_post): Call<Int>



}