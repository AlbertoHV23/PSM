package com.psm.lmaddoubts.models

data class tbl_post(
        var id_post:Int,
        var fk_usuario:Int,
        var fk_categoria:Int,
        var publicacion:String,
        var imagen: String?,
        var fecha: String?,
        var likes:Int)