package com.psm.lmaddoubts.Interface

import com.psm.lmaddoubts.models.tbl_categorias
import com.psm.lmaddoubts.models.tbl_post
import com.psm.lmaddoubts.models.tbl_usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface CategoriasInterface {
    //Servicios para consumir el Album
    @GET("Categoria/Categorias")
    fun getCategorias(): Call<List<tbl_categorias>>


}