package com.psm.lmaddoubts.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psm.lmaddoubts.Interface.PostInterface
import com.psm.lmaddoubts.Interface.RestEngine
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.adadpters.PostCategoriaAdapter
import com.psm.lmaddoubts.models.tbl_publicaciones
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriaActivity : AppCompatActivity() {

    private var context2: Context? = null
    private var adapter: PostCategoriaAdapter? = null
    var LISTAPublicaciones:List<tbl_publicaciones> = emptyList()
    lateinit var rvChat: RecyclerView

    var id_categoria:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categoria)

        rvChat=findViewById(R.id.rvPostCategory)
        rvChat.layoutManager = LinearLayoutManager(this)

        var categoria=  intent.getStringExtra("CATEGORIA")
        id_categoria = intent.getIntExtra("ID_CATEGORIA",0)

        var nameCa:TextView = findViewById(R.id.txt_ActividadCategoria)

        nameCa.text = categoria

        // Llamar categorias
        getPublicaciones()


    }

    //OBTENER PostCategorias
    private fun getPublicaciones() {
        val service: PostInterface =  RestEngine.getRestEngine().create(PostInterface::class.java)
        val result: Call<List<tbl_publicaciones>> = service.getCategoriaId(id_categoria)

        result.enqueue(object: Callback<List<tbl_publicaciones>> {

            override fun onFailure(call: Call<List<tbl_publicaciones>>, t: Throwable){
                //  Toast.makeText(this@HomeFragment,"Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<tbl_publicaciones>>, response: Response<List<tbl_publicaciones>>) {
                val arrayItems =  response.body()
                if (arrayItems != null) {
                    //LISTAPublicaciones = arrayItems
                    // getpublicaciones(arrayItems)
                    adapter = PostCategoriaAdapter(this, arrayItems)
                    rvChat.adapter = adapter

                }

            }
        })

    }
}