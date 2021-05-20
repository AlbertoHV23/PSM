package com.psm.lmaddoubts.models

data class tbl_publicaciones(
        var id_post:Int,
        var fk_usuario:Int,
        var fk_categoria:Int,
        var publicacion:String,
        var imagen:String,
        var fecha:String,
        var likes:Int,
        var usuario_nombre:String,
        var usuario_apellidos:String,
        var categoria_nombre:String,
        var imagen_perfil:String?
)