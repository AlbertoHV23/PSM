package com.psm.lmaddoubts.ui.Categories

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.adadpters.CategoriesAdapter
import com.psm.lmaddoubts.models.tbl_categorias

class CategoriesFragment : Fragment() {
    var grupos :List<tbl_categorias> = listOf(
            tbl_categorias(1,"Programacion Basica",2),
            tbl_categorias(1,"Programacion Avanzada",3),
            tbl_categorias(1,"POO",4),
    )

    private var context2: Context? = null
    private var adapter: CategoriesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var root =  inflater.inflate(R.layout.fragment_categories, container, false)

        val rvChat: RecyclerView =root.findViewById(R.id.rv_listaCategorias)
        rvChat.layoutManager = LinearLayoutManager(this.context2!!)
        this.adapter = CategoriesAdapter(this.context2!!, grupos)
        rvChat.adapter = this.adapter


        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context2 = context
    }

}