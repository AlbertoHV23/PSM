package com.psm.lmaddoubts.ui.Categories

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psm.lmaddoubts.Interface.CategoriasInterface
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.Interface.RestEngine
import com.psm.lmaddoubts.adadpters.CategoriesAdapter
import com.psm.lmaddoubts.models.tbl_categorias
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesFragment : Fragment() {
    var grupos :List<tbl_categorias> = listOf(
            tbl_categorias(1,"Programacion Basica",2),
            tbl_categorias(1,"Programacion Avanzada",3),
            tbl_categorias(1,"POO",4),
    )

    private var context2: Context? = null
    private var adapter: CategoriesAdapter? = null
    lateinit var rvChat: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var root =  inflater.inflate(R.layout.fragment_categories, container, false)
        getPost()
        rvChat =root.findViewById(R.id.rv_listaCategorias)
        rvChat.layoutManager = LinearLayoutManager(this.context2!!)


        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context2 = context
    }

    //OBTENER USUARIOS
    private fun getPost() {
        val service: CategoriasInterface =  RestEngine.getRestEngine().create(CategoriasInterface::class.java)
        val result: Call<List<tbl_categorias>> = service.getCategorias()

        result.enqueue(object: Callback<List<tbl_categorias>> {

            override fun onFailure(call: Call<List<tbl_categorias>>, t: Throwable){
                //  Toast.makeText(this@HomeFragment,"Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<tbl_categorias>>, response: Response<List<tbl_categorias>>) {
                val arrayItems =  response.body()
                if (arrayItems != null) {

                    adapter = CategoriesAdapter(context2!!, arrayItems)
                    rvChat.adapter = adapter

                }


            }
        })

    }

}