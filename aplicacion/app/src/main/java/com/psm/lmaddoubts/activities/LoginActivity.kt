package com.psm.lmaddoubts.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.RestEngine
import com.psm.lmaddoubts.Service
import com.psm.lmaddoubts.models.Encriptacion
import com.psm.lmaddoubts.models.tbl_usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class LoginActivity : AppCompatActivity() {
    lateinit var txt_email:EditText
    lateinit var txt_pass:EditText

    lateinit var email:String
    lateinit var password:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //BOTONOES
        val btn_activitySignIn:Button = findViewById(R.id.btn_activitySignIn)
        val btn_logear:Button = findViewById(R.id.btn_logear)
        //EDITTEXT
        txt_email = findViewById(R.id.txt_loginEmail)
        txt_pass = findViewById(R.id.txt_loginPass)

        getAlbums()


        btn_activitySignIn.setOnClickListener(){
            showignIn()
        }

        btn_logear.setOnClickListener(){
            ValidarRegistro()
        }


        val encriptado =  Encriptacion.cifar("hola","key123")
        println("Mensaje encriptado: $encriptado")

        val desincriptado =  Encriptacion.descifar(encriptado,"key123")
        println("Mensaje desincriptado: $desincriptado")

    }

    private  fun showignIn(){
        val intent:Intent = Intent(this, SingInActivity::class.java)
        startActivity(intent)

    }

    private  fun showHome(){
        val intent:Intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun ValidarRegistro(){
        if (txt_email.text.toString().isNotEmpty() || txt_pass.text.toString().isNotEmpty()){
            email = txt_email.text.toString()
            password = txt_pass.text.toString()
            println(email)
            println(password)
            showHome()
        }
        else{
            ShowAlert("Error", "Empty requirements")
        }
    }

    fun ShowAlert(title: String, msg: String) {
        val simpleDialog =  AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setIcon(R.drawable.ic_baseline_error_24)
                .setPositiveButton("Retry"){ _, _ ->
                    Toast.makeText(this, "Try again", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("Cancel"){ _, _->
                    Toast.makeText(this, "Cancel add user", Toast.LENGTH_LONG).show()
                }.create()

        simpleDialog.show()
    }



    //OBTENER ALBUMS
    private fun getAlbums(){
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<List<tbl_usuario>> = service.getAlbums()

        result.enqueue(object: Callback<List<tbl_usuario>> {

            override fun onFailure(call: Call<List<tbl_usuario>>, t: Throwable){
                Toast.makeText(this@LoginActivity,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<tbl_usuario>>, response: Response<List<tbl_usuario>>){
                val arrayItems =  response.body()
                var strMessage:String =  ""
                if (arrayItems != null){
                    for (item in arrayItems!!){
                        strMessage =  strMessage + item.id_usuario.toString() +  " - " + item.nombre + "\n"
                    }
                }

                Toast.makeText(this@LoginActivity,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }
}
