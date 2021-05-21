package com.psm.lmaddoubts

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psm.lmaddoubts.Interface.RespuestasInterface
import com.psm.lmaddoubts.Interface.RestEngine
import com.psm.lmaddoubts.adadpters.RespuestasAdapter
import com.psm.lmaddoubts.models.sharedPreferences.Companion.pref
import com.psm.lmaddoubts.models.tbl_respuestas
import com.psm.lmaddoubts.models.tbl_respuestasId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class RespuestasActivity : AppCompatActivity() {
    var ID_POST = 0
    var ID_USUARIO = 0

    private var context2: Context? = null
    private var adapter: RespuestasAdapter? = null
    lateinit var rvChat: RecyclerView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_LOGIN)
        setContentView(R.layout.activity_respuestas)
        var publicacion=  intent.getStringExtra("PUBLICACION")
        var id_post=  intent.getStringExtra("ID_POST")
        var id_usuario = pref.getIdUsuario()

        rvChat=findViewById(R.id.rv_respuestas)
        rvChat.layoutManager = LinearLayoutManager(this)
        if (id_post != null) {
            ID_POST = id_post.toInt()
        }
        if (id_usuario != null) {
            ID_USUARIO = id_usuario.toInt()
        }

        var txt_header:TextView = findViewById(R.id.txt_ActividadRespuestas)
        var txt_respuesta:EditText = findViewById(R.id.txt_respuesta)
        var btn_sendRespuesta:Button = findViewById(R.id.btn_sendRespuesta)
        txt_header.text = publicacion

        btn_sendRespuesta.setOnClickListener(){
            var respuesta:tbl_respuestas = tbl_respuestas(0,ID_USUARIO,ID_POST,txt_respuesta.text.toString(),null,"")
            saveUser(respuesta)
        }

        getMisPublicaciones()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveUser(user: tbl_respuestas){
        val service: RespuestasInterface =  RestEngine.getRestEngine().create(RespuestasInterface::class.java)
        val result: Call<Int> = service.saveRespuestas(user)

        result.enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(this@RespuestasActivity,"Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                //val arrayItems =  response.body()
                Toast.makeText(this@RespuestasActivity,"OK", Toast.LENGTH_LONG).show()
                //adapter = RespuestasAdapter(this@RespuestasActivity, arrayItems)
                //rvChat.adapter = adapter



            }
        })
    }


    //OBTENER PostCategorias
    private fun getMisPublicaciones() {
        val service: RespuestasInterface =  RestEngine.getRestEngine().create(RespuestasInterface::class.java)
        var json = tbl_respuestasId(id_post = ID_POST)
        val result: Call<List<tbl_respuestas>> = service.getRespuestasPost(json)

        result.enqueue(object: Callback<List<tbl_respuestas>> {

            override fun onFailure(call: Call<List<tbl_respuestas>>, t: Throwable){
            }

            override fun onResponse(call: Call<List<tbl_respuestas>>, response: Response<List<tbl_respuestas>>) {
                val arrayItems =  response.body()
                if (arrayItems != null) {
                    println(arrayItems)
                    adapter = RespuestasAdapter(this@RespuestasActivity, arrayItems)
                    rvChat.adapter = adapter

                }

            }
        })

    }

}