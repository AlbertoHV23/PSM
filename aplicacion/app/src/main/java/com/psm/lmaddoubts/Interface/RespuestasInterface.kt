package com.psm.lmaddoubts.Interface

import com.psm.lmaddoubts.models.tbl_respuestas
import com.psm.lmaddoubts.models.tbl_usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface RespuestasInterface {
    //Servicios para consumir el Album
    @GET("Respuesta/RespuestasCN")
    fun getRespuestas(): Call<List<tbl_respuestas>>


    @Headers("Content-Type: application/json")
    @POST("Respuesta/Save")
    fun saveRespuestas(@Body albumData: tbl_respuestas): Call<Int>

}