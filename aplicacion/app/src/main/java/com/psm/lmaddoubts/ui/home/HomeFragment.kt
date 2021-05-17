package com.psm.lmaddoubts.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psm.lmaddoubts.PostInterface
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.RestEngine
import com.psm.lmaddoubts.UserService
import com.psm.lmaddoubts.adadpters.CategoriesAdapter
import com.psm.lmaddoubts.adadpters.HomeAdapter
import com.psm.lmaddoubts.models.tbl_categorias
import com.psm.lmaddoubts.models.tbl_post
import com.psm.lmaddoubts.models.tbl_publicaciones
import com.psm.lmaddoubts.models.tbl_usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    var grupos :List<tbl_post> = listOf(
        tbl_post(1,1,1,"Hola","ssd","sdasd",0),
            tbl_post(1,1,1,"Adios","ssd","sdasd",0)
    )
    private var context2: Context? = null
    private var adapter: HomeAdapter? = null
    lateinit var LISTAPOSTS:List<tbl_post>
    lateinit var LISTAPublicaciones:List<tbl_publicaciones>

    lateinit var rvChat: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //getPost()
        getPublicaciones()
        println("lISTA DE POST $LISTAPOSTS")
        println("LISTA DE POST PERRA $LISTAPublicaciones")

        var root =  inflater.inflate(R.layout.fragment_home, container, false)

        rvChat=root.findViewById(R.id.rv_home)
        rvChat.layoutManager = LinearLayoutManager(this.context2!!)





        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context2 = context
    }

    //OBTENER USUARIOS
    private fun getPublicaciones() {
        val service: PostInterface =  RestEngine.getRestEngine().create(PostInterface::class.java)
        val result: Call<List<tbl_publicaciones>> = service.getPublicaciones()

        result.enqueue(object: Callback<List<tbl_publicaciones>> {

            override fun onFailure(call: Call<List<tbl_publicaciones>>, t: Throwable){
              //  Toast.makeText(this@HomeFragment,"Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<tbl_publicaciones>>, response: Response<List<tbl_publicaciones>>) {
                val arrayItems =  response.body()
                if (arrayItems != null) {
                    LISTAPublicaciones = arrayItems
                   // getpublicaciones(arrayItems)
                    adapter = HomeAdapter(context2!!, arrayItems)
                    rvChat.adapter = adapter

                }



            }
        })

    }

    //OBTENER USUARIOS
    private fun getPost() {
        val service: PostInterface =  RestEngine.getRestEngine().create(PostInterface::class.java)
        val result: Call<List<tbl_post>> = service.getPosts()

        result.enqueue(object: Callback<List<tbl_post>> {

            override fun onFailure(call: Call<List<tbl_post>>, t: Throwable){
                //  Toast.makeText(this@HomeFragment,"Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<tbl_post>>, response: Response<List<tbl_post>>) {
                val arrayItems =  response.body()
                if (arrayItems != null) {
                    LISTAPOSTS = arrayItems
                    getpublicaciones(arrayItems)
                  //  adapter = HomeAdapter(context2!!, arrayItems)
                    rvChat.adapter = adapter

                }


            }
        })

    }

    fun getpublicaciones(item:List<tbl_post>){
       this.LISTAPOSTS = item
    }

}