package com.psm.lmaddoubts

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


}