package com.psm.lmaddoubts.adadpters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.models.tbl_categorias

class CategoriesAdapter(val context: Context, var LISTA:List<tbl_categorias>): RecyclerView.Adapter<CategoriesAdapter.Holder>() {
    inner class Holder(val view: View):RecyclerView.ViewHolder(view), View.OnClickListener{
        fun render(superHero: tbl_categorias) {
            var categoria: TextView = view?.findViewById(R.id.txt_listCategorie)

            if (superHero != null){
            categoria.text = superHero.categoria

            }



        }

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when(v!!.id){


            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(layoutInflater.inflate(R.layout.item_lista_categorias,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.render(LISTA[position])
    }

    override fun getItemCount(): Int {
        return LISTA.size
    }
}