package com.psm.lmaddoubts.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psm.lmaddoubts.Interface.PostInterface
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.Interface.RestEngine
import com.psm.lmaddoubts.Interface.UserService
import com.psm.lmaddoubts.adadpters.HomeAdapter
import com.psm.lmaddoubts.models.sharedPreferences.Companion.pref
import com.psm.lmaddoubts.models.tbl_post
import com.psm.lmaddoubts.models.tbl_publicaciones
import com.psm.lmaddoubts.models.tbl_usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HomeFragment : Fragment() {

    var grupos :List<tbl_post> = listOf(
        tbl_post(1,1,1,"Hola","ssd","sdasd",0),
            tbl_post(1,1,1,"Adios","ssd","sdasd",0)
    )
    private var context2: Context? = null
    private var adapter: HomeAdapter? = null
    var LISTAPOSTS:List<tbl_post> = emptyList()
    var LISTAPublicaciones:List<tbl_publicaciones> = emptyList()
    var fk_categoria:Int = 0
    lateinit var rvChat: RecyclerView
    private val fileResult = 1
    var id_usuario_int = 0



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getPublicaciones()
        var root =  inflater.inflate(R.layout.fragment_home, container, false)
        var id_user = pref.getName()
        if (id_user != null) {
            id_usuario_int = id_user.toInt()

        }

        val spinner: Spinner = root.findViewById(R.id.combo)

        var lista = resources.getStringArray(R.array.Categorias)
        var adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,lista)

        var bnt_image:Button = root.findViewById(R.id.btn_postImage)
        var bnt_postear:Button = root.findViewById(R.id.btn_postear)

        var txt_publicacion:EditText = root.findViewById(R.id.editTextTextMultiLine)



        spinner.adapter = adapter

        rvChat=root.findViewById(R.id.rv_home)
        rvChat.layoutManager = LinearLayoutManager(this.context2!!)


        spinner.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                fk_categoria = position + 1
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }


        bnt_image.setOnClickListener(){
            filemanager()
        }

        bnt_postear.setOnClickListener(){

            var post = tbl_post(0,id_usuario_int,fk_categoria,txt_publicacion.text.toString(),null,null,0)
            txt_publicacion.setText("")
            Toast.makeText(this.context2, "Posted correctly", Toast.LENGTH_SHORT).show()
            saveUser(post)
        }




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
                    println(arrayItems.size)

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

    private fun filemanager(){
        //ABRE LA VENTA DEL FILENAMAGER PARA SELECCIONAR LA IMAGEN
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2 ){
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
        }
        intent.type = "*/*"
        startActivityForResult(intent,fileResult)
    }

    //trae el elemento seleccionado del file manager
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileResult) {
            if (resultCode == Activity.RESULT_OK && data != null) {

                val clipData = data.clipData

                if (clipData != null){
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        uri?.let { println(it) }
                    }
                }else {
                    val uri = data.data
                    uri?.let {  println(it) }
                }

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
                //Toast.makeText(this@HomeFragment,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                getPublicaciones()

            }
        })
    }


}