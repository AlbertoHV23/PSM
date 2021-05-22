package com.psm.lmaddoubts.ui.Published

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psm.lmaddoubts.Interface.PostInterface
import com.psm.lmaddoubts.Interface.RestEngine
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.adadpters.HomeAdapter
import com.psm.lmaddoubts.models.UserAplication.Companion.pref
import com.psm.lmaddoubts.models.tbl_publicaciones
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PublishedFragment : Fragment() {
    var fk_usuario:Int = 0
    private var adapter: HomeAdapter? = null
    lateinit var rvChat: RecyclerView
    private var context2: Context? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var root =  inflater.inflate(R.layout.fragment_published, container, false)

        rvChat=root.findViewById(R.id.rv_mypost)
        rvChat.layoutManager = LinearLayoutManager(context)

        var id = pref.getIdUsuario()
        if (id != null) {
            fk_usuario = id.toInt()
        }

        getMisPublicaciones()


        return root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context2 = context
    }

    //OBTENER PostCategorias
    private fun getMisPublicaciones() {
        val service: PostInterface =  RestEngine.getRestEngine().create(PostInterface::class.java)
        var json = tbl_publicaciones(0,fk_usuario,0,"","null","null",0,"",",","","")
        val result: Call<List<tbl_publicaciones>> = service.getPostUsuario(json)

        result.enqueue(object: Callback<List<tbl_publicaciones>> {

            override fun onFailure(call: Call<List<tbl_publicaciones>>, t: Throwable){
            }

            override fun onResponse(call: Call<List<tbl_publicaciones>>, response: Response<List<tbl_publicaciones>>) {
                val arrayItems =  response.body()
                if (arrayItems != null) {
                    println(arrayItems)

                    adapter = HomeAdapter(context2!!, arrayItems)
                    rvChat.adapter = adapter

                }

            }
        })

    }

}