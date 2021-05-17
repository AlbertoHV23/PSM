package com.psm.lmaddoubts

import com.psm.lmaddoubts.models.tbl_categorias
import com.psm.lmaddoubts.models.tbl_post
import retrofit2.Call
import retrofit2.http.GET

interface CategoriasInterface {
    //Servicios para consumir el Album
    @GET("Categoria/Categorias")
    fun getCategorias(): Call<List<tbl_categorias>>
}