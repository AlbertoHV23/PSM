package com.psm.lmaddoubts.models

data class tbl_usuario(
        var id_usuario: Int ? =  null,
        var nombre:String? =  null,
        var apellidos:String? =  null,
        var email:String? =  null,
        var contrasena:String? =  null,
        var avatar:String? =  null
){}