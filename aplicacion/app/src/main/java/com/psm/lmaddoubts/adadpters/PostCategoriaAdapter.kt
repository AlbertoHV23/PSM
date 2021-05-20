package com.psm.lmaddoubts.adadpters

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.psm.lmaddoubts.Interface.ImageUtilities
import com.psm.lmaddoubts.activities.CategoriaActivity
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.models.tbl_publicaciones
import retrofit2.Callback
import java.util.*

class PostCategoriaAdapter(val context: Callback<List<tbl_publicaciones>>, var LISTA:List<tbl_publicaciones>): RecyclerView.Adapter<PostCategoriaAdapter.Holder>() {

    inner class Holder(val view: View):RecyclerView.ViewHolder(view), View.OnClickListener{
        var likes = 0
        lateinit var txt_likes:TextView
        lateinit var btn_like: Button


        @RequiresApi(Build.VERSION_CODES.O)
        fun render(superHero: tbl_publicaciones) {
            var txt_publicacion:TextView = view?.findViewById(R.id.txt_PostVPublicacion)
            var txt_nombreUusario:TextView = view?.findViewById(R.id.txt_PostVUser)
            var txt_categoria:TextView = view?.findViewById(R.id.txt_PostVcategoria)


            var avatar: ImageView = view?.findViewById(R.id.img_PostVAvatar)
            var post_imagen: ImageView = view?.findViewById(R.id.img_postImagen)

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

                val strImage: String? = superHero.imagen_perfil?.replace("data:image/png;base64,","")
                var byteArray =  Base64.getDecoder().decode(strImage)
                avatar!!.setImageBitmap(ImageUtilities.getBitMapFromByteArray(byteArray))


                if (superHero.imagen == null){
                    post_imagen.setVisibility(View.GONE);
                }
                else{
                    val strImage: String? = superHero.imagen?.replace("data:image/png;base64,","")
                    var byteArray =  Base64.getDecoder().decode(strImage)
                    avatar!!.setImageBitmap(ImageUtilities.getBitMapFromByteArray(byteArray))
                }





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
        return Holder(layoutInflater.inflate(R.layout.item_home,parent,false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.render(LISTA[position])
    }

    override fun getItemCount(): Int {
        return LISTA.size
    }
}