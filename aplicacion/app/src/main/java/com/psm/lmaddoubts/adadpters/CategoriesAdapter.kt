package com.psm.lmaddoubts.adadpters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.psm.lmaddoubts.CategoriaActivity
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.models.tbl_categorias

class CategoriesAdapter(val context: Context, var LISTA:List<tbl_categorias>): RecyclerView.Adapter<CategoriesAdapter.Holder>() {

    inner class Holder(val view: View):RecyclerView.ViewHolder(view), View.OnClickListener{
        private var Categoria:String = ""

        fun render(superHero: tbl_categorias) {
            var txt_nombre:TextView = view?.findViewById(R.id.txt_ListaCategoria)
            var txt_semestre:TextView = view?.findViewById(R.id.txt_listaSemestre)
            if (superHero != null){
                txt_nombre.text= superHero.categoria
                txt_semestre.text = superHero.semestre.toString() + "°"

                Categoria = superHero.categoria


            }



        }
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when(v!!.id){
                R.id.item_categoria -> {
                    val  activityIntent =  Intent(context, CategoriaActivity::class.java)
                    activityIntent.putExtra("CATEGORIA",this.Categoria)

                    context.startActivity(activityIntent)
                }
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