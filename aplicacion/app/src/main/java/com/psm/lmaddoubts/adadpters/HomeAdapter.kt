
package com.psm.lmaddoubts.adadpters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.psm.lmaddoubts.Interface.PostInterface
import com.psm.lmaddoubts.Interface.RestEngine
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.models.tbl_post
import com.psm.lmaddoubts.models.tbl_publicaciones
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeAdapter(val context: Context, var LISTA:List<tbl_publicaciones>): RecyclerView.Adapter<HomeAdapter.Holder>() {


    inner class Holder(val view: View):RecyclerView.ViewHolder(view), View.OnClickListener{
        private var Categoria:String = ""
        var likes = 0
        lateinit var txt_likes:TextView
        lateinit var btn_like:Button
        private lateinit var postTem:tbl_post

        fun render(superHero: tbl_publicaciones) {
            var txt_publicacion:TextView = view?.findViewById(R.id.txt_PostVPublicacion)
            var txt_nombreUusario:TextView = view?.findViewById(R.id.txt_PostVUser)
            var txt_categoria:TextView = view?.findViewById(R.id.txt_PostVcategoria)

            var avatar:ImageView = view?.findViewById(R.id.img_PostVAvatar)
            var post_imagen:ImageView = view?.findViewById(R.id.img_postImagen)

            txt_likes= view?.findViewById(R.id.textView9)

            btn_like= view?.findViewById(R.id.btn_like)

            btn_like.setOnClickListener(){
                this.likes++
                txt_likes.text = "$likes doubts"
            }


            if (superHero != null){
                this.postTem = tbl_post(superHero.id_post,superHero.fk_usuario,superHero.fk_categoria,superHero.publicacion,superHero.imagen,superHero.fecha,this.likes)
                txt_publicacion.text = superHero.publicacion
                txt_likes.text = "$likes doubts"
                txt_nombreUusario.text = "${superHero.usuario_nombre} ${superHero.usuario_apellidos}"
                txt_categoria.text = superHero.categoria_nombre
                this.likes = superHero.likes

                if (superHero.avatar == null){
                    post_imagen.setVisibility(View.INVISIBLE);
                }




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
                    println(this.postTem)
                    //esta pendiente este
                  //  saveUser(this.postTem)
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

    private fun saveUser(user: tbl_post){

        //val encodedString:String =  Base64.getEncoder().encodeToString(this.imgArray)

        //val strEncodeImage:String = "data:image/png;base64," + encodedString

        //SE CONSTRUYE EL OBJECTO A ENVIAR,  ESTO DEPENDE DE COMO CONSTRUYAS EL SERVICIO
        // SI TU SERVICIO POST REQUIERE DOS PARAMETROS HACER UN OBJECTO CON ESOS DOS PARAMETROS
        // user.avatar = strEncodeImage


        val service: PostInterface =  RestEngine.getRestEngine().create(PostInterface::class.java)
        val result: Call<Int> = service.saveUsuarios(user)

        result.enqueue(object: Callback<Int>{
            override fun onFailure(call: Call<Int>, t: Throwable) {
                //Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                //Toast.makeText(this@HomeFragment,"OK",Toast.LENGTH_LONG).show()

                // showHome(response.body().toString())

            }
        })
    }
}