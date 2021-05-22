package com.psm.lmaddoubts.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.psm.lmaddoubts.models.tbl_publicaciones
import com.psm.lmaddoubts.models.tbl_usuario
import java.lang.Exception

class DataDbHelper (var context: Context): SQLiteOpenHelper(context,SetDB.DB_NAME,null,SetDB.DB_VERSION){

    override fun onCreate(db: SQLiteDatabase?){
        //SI NO EXISTE LA BASE DE DATOS LA CREA
        try{

            val createAlbumTable:String =  "CREATE TABLE " + SetDB.TBL_USUARIO.TABLE_NAME + "(" +
                    SetDB.TBL_USUARIO.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SetDB.TBL_USUARIO.COL_NOMBRE + " VARCHAR(256)," +
                    SetDB.TBL_USUARIO.COL_APELLIDO + " VARCHAR(256)," +
                    SetDB.TBL_USUARIO.COL_EMAIL + "  VARCHAR(256)," +
                    SetDB.TBL_USUARIO.COL_PASSWORD + "  VARCHAR(256)," +
                    SetDB.TBL_USUARIO.COL_IMAGEN + " BLOB)"

            db?.execSQL(createAlbumTable)

            val createGenreTable:String =  "CREATE TABLE " + SetDB.TBL_POST.TABLE_NAME + "(" +
                    SetDB.TBL_POST.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SetDB.TBL_POST.COL_FKUSUARIO + " INTEGER," +
                    SetDB.TBL_POST.COL_FKCATEGORIA + " INTEGER," +
                    SetDB.TBL_POST.COL_PUBLICACION + "  VARCHAR(256)," +
                    SetDB.TBL_POST.COL_IMAGEN + " BLOB," +
                    SetDB.TBL_POST.COL_FECHA + "  VARCHAR(256)," +
                    SetDB.TBL_POST.COL_LIKES + " INTEGER," +
                    SetDB.TBL_POST.COL_NOMBREUSURIO + "  VARCHAR(256)," +
                    SetDB.TBL_POST.COL_APELLIDOUSUARIO + "  VARCHAR(256)," +
                    SetDB.TBL_POST.COL_NOMBRECATEGORIA + "  VARCHAR(256)," +
                    SetDB.TBL_POST.COL_IMAGENPERFIL + " BLOB)"

            db?.execSQL(createGenreTable)

            println("entro y creo tablas")
            Log.e("ENTRO","CREO TABLAS")

        }catch (e: Exception){
            println("No entro")
            Log.e("Execption", e.toString())
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


    public fun insertAlbum(usuario:tbl_usuario):Boolean{

        val dataBase:SQLiteDatabase = this.writableDatabase
        val values: ContentValues = ContentValues()
        var boolResult:Boolean =  true

        values.put(SetDB.TBL_USUARIO.COL_NOMBRE,usuario.nombre)
        values.put(SetDB.TBL_USUARIO.COL_APELLIDO,usuario.apellidos)
        values.put(SetDB.TBL_USUARIO.COL_EMAIL,usuario.email)
        values.put(SetDB.TBL_USUARIO.COL_PASSWORD,usuario.contrasena)
        values.put(SetDB.TBL_USUARIO.COL_IMAGEN,usuario.avatar)

        try {
            val result =  dataBase.insert(SetDB.TBL_USUARIO.TABLE_NAME, null, values)

            if (result == (0).toLong()) {
                Toast.makeText(this.context, "Failed", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this.context, "Success", Toast.LENGTH_SHORT).show()
            }

        }catch (e: Exception){
            Log.e("Execption", e.toString())
            boolResult =  false
        }

        dataBase.close()

        return boolResult
    }

    public fun getListOfAlbum():MutableList<tbl_usuario>{
        val List:MutableList<tbl_usuario> = ArrayList()

        val dataBase:SQLiteDatabase = this.writableDatabase

        //QUE COLUMNAS QUEREMOS QUE ESTE EN EL SELECT
        val columns:Array<String> =  arrayOf(
            SetDB.TBL_USUARIO.COL_ID,
            SetDB.TBL_USUARIO.COL_NOMBRE,
            SetDB.TBL_USUARIO.COL_APELLIDO,
            SetDB.TBL_USUARIO.COL_EMAIL,
            SetDB.TBL_USUARIO.COL_PASSWORD,
            SetDB.TBL_USUARIO.COL_IMAGEN)

        val data =  dataBase.query(SetDB.TBL_USUARIO.TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            SetDB.TBL_USUARIO.COL_ID + " ASC")

        // SI NO TIENE DATOS DEVUELVE FALSO
        //SE MUEVE A LA PRIMERA POSICION DE LOS DATOS
        if(data.moveToFirst()){

            do{
                val album:tbl_usuario = tbl_usuario()
                album.id_usuario = data.getString(data.getColumnIndex(SetDB.TBL_USUARIO.COL_ID)).toInt()
                album.nombre =  data.getString(data.getColumnIndex(SetDB.TBL_USUARIO.COL_NOMBRE)).toString()
                album.apellidos = data.getString(data.getColumnIndex(SetDB.TBL_USUARIO.COL_APELLIDO)).toString()
                album.email =  data.getString(data.getColumnIndex(SetDB.TBL_USUARIO.COL_EMAIL)).toString()
                album.contrasena =  data.getString(data.getColumnIndex(SetDB.TBL_USUARIO.COL_PASSWORD)).toString()
                album.avatar = data.getBlob(data.getColumnIndex(SetDB.TBL_USUARIO.COL_IMAGEN)).toString()

                List.add(album)

                //SE MUEVE A LA SIGUIENTE POSICION, REGRESA FALSO SI SE PASO DE LA CANTIDAD DE DATOS
            }while (data.moveToNext())

        }
        return  List
    }


    public fun getAlbum(email:String):tbl_usuario?{
        var album:tbl_usuario? = null
        val dataBase:SQLiteDatabase = this.writableDatabase

        //QUE COLUMNAS QUEREMOS QUE ESTE EN EL SELECT
        val columns:Array<String> =  arrayOf(
            SetDB.TBL_USUARIO.COL_ID,
            SetDB.TBL_USUARIO.COL_NOMBRE,
            SetDB.TBL_USUARIO.COL_APELLIDO,
            SetDB.TBL_USUARIO.COL_EMAIL,
            SetDB.TBL_USUARIO.COL_PASSWORD,
            SetDB.TBL_USUARIO.COL_IMAGEN)

        val where:String =  SetDB.TBL_USUARIO.COL_EMAIL  + " = '${email}'"
        val data =  dataBase.query(
            SetDB.TBL_USUARIO.TABLE_NAME,
            columns,
            where,
            null,
            null,
            null,
            null)

        if(data.moveToFirst()){
            album = tbl_usuario()
            album.id_usuario = data.getString(data.getColumnIndex(SetDB.TBL_USUARIO.COL_ID)).toInt()
            album.nombre =  data.getString(data.getColumnIndex(SetDB.TBL_USUARIO.COL_NOMBRE)).toString()
            album.apellidos = data.getString(data.getColumnIndex(SetDB.TBL_USUARIO.COL_APELLIDO)).toString()
            album.email =  data.getString(data.getColumnIndex(SetDB.TBL_USUARIO.COL_EMAIL)).toString()
            album.contrasena =   data.getString(data.getColumnIndex(SetDB.TBL_USUARIO.COL_PASSWORD)).toString()
            album.avatar =  data.getString(data.getColumnIndex(SetDB.TBL_USUARIO.COL_IMAGEN)).toString()

        }

        data.close()
        return album
    }




    public fun insertPost(usuario:tbl_publicaciones):Boolean{

        val dataBase:SQLiteDatabase = this.writableDatabase
        val values: ContentValues = ContentValues()
        var boolResult:Boolean =  true

        values.put(SetDB.TBL_POST.COL_FKUSUARIO,usuario.fk_usuario)
        values.put(SetDB.TBL_POST.COL_FKCATEGORIA,usuario.fk_categoria)
        values.put(SetDB.TBL_POST.COL_PUBLICACION,usuario.publicacion)
        values.put(SetDB.TBL_POST.COL_IMAGEN,usuario.imagen)
        values.put(SetDB.TBL_POST.COL_FECHA,usuario.fecha)
        values.put(SetDB.TBL_POST.COL_LIKES,usuario.likes)
        values.put(SetDB.TBL_POST.COL_NOMBREUSURIO,usuario.usuario_nombre)
        values.put(SetDB.TBL_POST.COL_APELLIDOUSUARIO,usuario.usuario_apellidos)
        values.put(SetDB.TBL_POST.COL_NOMBRECATEGORIA,usuario.categoria_nombre)
        values.put(SetDB.TBL_POST.COL_IMAGENPERFIL,usuario.imagen_perfil)


        try {
            val result =  dataBase.insert(SetDB.TBL_POST.TABLE_NAME, null, values)

            if (result == (0).toLong()) {
                Toast.makeText(this.context, "Failed", Toast.LENGTH_SHORT).show()
            }
            else {
                //Toast.makeText(this.context, "Success", Toast.LENGTH_SHORT).show()
            }

        }catch (e: Exception){
            Log.e("Execption", e.toString())
            boolResult =  false
        }

        dataBase.close()

        return boolResult
    }

    public fun getListaPublicaciones():MutableList<tbl_publicaciones>{
        val List:MutableList<tbl_publicaciones> = ArrayList()

        val dataBase:SQLiteDatabase = this.writableDatabase

        //QUE COLUMNAS QUEREMOS QUE ESTE EN EL SELECT
        val columns:Array<String> =  arrayOf(
                    SetDB.TBL_POST.COL_ID,
                    SetDB.TBL_POST.COL_FKUSUARIO,
                    SetDB.TBL_POST.COL_FKCATEGORIA,
                    SetDB.TBL_POST.COL_PUBLICACION,
                    SetDB.TBL_POST.COL_IMAGEN,
                    SetDB.TBL_POST.COL_FECHA,
                    SetDB.TBL_POST.COL_LIKES,
                    SetDB.TBL_POST.COL_NOMBREUSURIO,
                    SetDB.TBL_POST.COL_APELLIDOUSUARIO,
                    SetDB.TBL_POST.COL_NOMBRECATEGORIA,
                    SetDB.TBL_POST.COL_IMAGENPERFIL)

        val data =  dataBase.query(
            SetDB.TBL_POST.TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null)

        // SI NO TIENE DATOS DEVUELVE FALSO
        //SE MUEVE A LA PRIMERA POSICION DE LOS DATOS
        if(data.moveToFirst()){

            do{
                var album:tbl_publicaciones = tbl_publicaciones(
                    data.getString(data.getColumnIndex(SetDB.TBL_POST.COL_ID)).toInt(),
                    data.getString(data.getColumnIndex(SetDB.TBL_POST.COL_FKUSUARIO)).toInt(),
                    data.getString(data.getColumnIndex(SetDB.TBL_POST.COL_FKCATEGORIA)).toInt(),
                    data.getString(data.getColumnIndex(SetDB.TBL_POST.COL_PUBLICACION)).toString(),
                  null,
                    data.getString(data.getColumnIndex(SetDB.TBL_POST.COL_FECHA)).toString(),
                    data.getString(data.getColumnIndex(SetDB.TBL_POST.COL_LIKES)).toInt(),
                    data.getString(data.getColumnIndex(SetDB.TBL_POST.COL_NOMBREUSURIO)).toString(),
                    data.getString(data.getColumnIndex(SetDB.TBL_POST.COL_APELLIDOUSUARIO)).toString(),
                    data.getString(data.getColumnIndex(SetDB.TBL_POST.COL_NOMBRECATEGORIA)).toString(),
                    data.getString(data.getColumnIndex(SetDB.TBL_POST.COL_IMAGENPERFIL)).toString())

                List.add(album)

                //SE MUEVE A LA SIGUIENTE POSICION, REGRESA FALSO SI SE PASO DE LA CANTIDAD DE DATOS
            }while (data.moveToNext())

        }
        return  List
    }


    public fun deletePosts():Boolean{
        val db = this.writableDatabase
        var boolResult:Boolean =  false
        try{
            db.delete(SetDB.TBL_POST.TABLE_NAME, null, null);


        }catch (e: Exception){

            Log.e("Execption", e.toString())
        }

        return  boolResult
    }



}