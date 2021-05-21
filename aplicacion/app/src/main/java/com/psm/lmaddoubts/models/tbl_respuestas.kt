package com.psm.lmaddoubts.models

data class tbl_respuestas(
        var id_respuesta:Int,
        var fk_usuario:Int,
        var fk_post:Int,
        var respuesta:String,
        var fecha: String?,
        var nombre_usuario: String
        )