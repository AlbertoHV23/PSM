package com.psm.lmaddoubts.sqlite


//CLASE UTILIZADA PARA DEFINIR EL ESQUEMA DE NUESTRA BASE DE DATOS
class SetDB {

    //DECLARAMOS  EL NOMBRE Y VERSION DE TAL FOR QUE PUEDA SER VISIBLES PARA CUALQUIER CLASE
    companion object{
        val DB_NAME =  "lmadDoubts"
        val DB_VERSION =  1
    }

    //VAMOS ES DEFINIR EL ESQUEMA DE UNA DE LAS TABLAS
    abstract class TBL_USUARIO{
        //DEFINIMOS LOS ATRIBUTOS DE LA CLASE USANDO CONTANTES
        companion object{
            val TABLE_NAME = "USUARIOS"
            val COL_ID =  "id_usuario"
            val COL_NOMBRE =  "nombre"
            val COL_APELLIDO = "apellidos"
            val COL_EMAIL =  "email"
            val COL_PASSWORD =  "contrasena"
            val COL_IMAGEN=  "avatar" // byte Array image
        }
    }

    //VAMOS ES DEFINIR EL ESQUEMA DE UNA DE LAS TABLAS
    abstract class TBL_POST{
        //DEFINIMOS LOS ATRIBUTOS DE LA CLASE USANDO CONTANTES
        companion object{
            val TABLE_NAME = "POST"
            val COL_ID =  "id_post"
            val COL_FKUSUARIO =  "fk_usuario"
            val COL_FKCATEGORIA = "fk_categoria"
            val COL_PUBLICACION =  "publicacion"
            val COL_IMAGEN =  "imagen"
            val COL_FECHA=  "fecha" // byte Array image
            val COL_LIKES=  "likes"
            val COL_NOMBREUSURIO =  "usuario_nombre"
            val COL_APELLIDOUSUARIO = "usuario_apellidos"
            val COL_NOMBRECATEGORIA =  "categoria_nombre"
            val COL_IMAGENPERFIL=  "imagen_perfil"
        }
    }



}