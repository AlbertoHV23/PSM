
package com.psm.lmaddoubts.adadpters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.psm.lmaddoubts.CategoriaActivity
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.models.tbl_categorias
import com.psm.lmaddoubts.models.tbl_post
import com.psm.lmaddoubts.models.tbl_publicaciones
import com.psm.lmaddoubts.models.tbl_usuario

class HomeAdapter(val context: Context, var LISTA:List<tbl_publicaciones>): RecyclerView.Adapter<HomeAdapter.Holder>() {


    inner class Holder(val view: View):RecyclerView.ViewHolder(view), View.OnClickListener{
        private var Categoria:String = ""
        var likes = 0
        lateinit var txt_likes:TextView
        lateinit var btn_like:Button

        fun render(superHero: tbl_publicaciones) {
            var txt_publicacion:TextView = view?.findViewById(R.id.txt_PostVPublicacion)
            var txt_nombreUusario:TextView = view?.findViewById(R.id.txt_PostVUser)
            var txt_categoria:TextView = view?.findViewById(R.id.txt_PostVcategoria)

            txt_likes= view?.findViewById(R.id.textView9)

            btn_like= view?.findViewById(R.id.btn_like)

            btn_like.setOnClickListener(){
                this.likes++
                txt_likes.text = "$likes doubts"
            }


            if (superHero != null){
                txt_publicacion.text = superHero.publicacion
                txt_likes.text = "$likes doubts"
                txt_nombreUusario.text = "${superHero.usuario_nombre} ${superHero.usuario_apellidos}"
                txt_categoria.text = superHero.categoria_nombre

                // txt_publicacion.text = superHero.publicacion




            }



        }
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when(v!!.id){
                R.id.btn_like -> {
                  this.likes++
                    txt_likes.text = "$likes doubts"
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(layoutInflater.inflate(R.layout.item_home,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.render(LISTA[position])
    }

    override fun getItemCount(): Int {
        return LISTA.size
    }
}