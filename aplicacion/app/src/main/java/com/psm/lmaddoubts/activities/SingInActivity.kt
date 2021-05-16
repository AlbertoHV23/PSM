package com.psm.lmaddoubts.activities

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.annotation.RequiresApi
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.models.ApiService
import com.psm.lmaddoubts.models.RestEngine
import com.psm.lmaddoubts.models.tbl_usuarios
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*

class SingInActivity : AppCompatActivity() {
    var txtId: TextView? = null
    var imageUI: ImageView? =  null
    var imgArray:ByteArray? =  null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        val btn_login: Button = findViewById(R.id.btn_gologin)
        val btn_registrar: Button = findViewById(R.id.btn_registar)
        val btn_opencam: ImageButton = findViewById(R.id.btnCamera)

        val txt_name: EditText = findViewById(R.id.txt_nameSign)
        val txt_apellidos: EditText = findViewById(R.id.txt_apellidos)
        val txt_loginEmail: EditText = findViewById(R.id.txt_loginEmail)
        val txt_loginPass: EditText = findViewById(R.id.txt_loginPass)
        imageUI =  findViewById(R.id.imgUI)

        getAlbums()

        btn_login.setOnClickListener(){
            onBackPressed()
        }

        btn_registrar.setOnClickListener(){
            var user:tbl_usuarios = tbl_usuarios(0,
                    txt_name.text.toString(),
                    txt_apellidos.text.toString(),
                    txt_loginEmail.text.toString(),
                    txt_loginPass.text.toString(),
                    null)
            saveAlbum(user)
        }

        btn_opencam.setOnClickListener(){
            openCamera()
        }


    }

    companion object {
        //Estos número tu los eliges como mejor funcione para ti, no necesariamente tienen que ser 1000, puede
        // ser 1,2,3
        //Lo importante es ser congruente en su uso
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
        //camera code
        private val CAMERA_CODE = 1002;
    }

    private  fun showHome(){
        val intent: Intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)

    }

    private fun openCamera(){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_CODE)
    }

    override fun onActivityResult(requestcode: Int, resultcode: Int, data: Intent?) {
        super.onActivityResult(requestcode, resultcode, intent)

        if (resultcode == Activity.RESULT_OK) {
            //RESPUESTA DE LA CÁMARA CON TIENE LA IMAGEN
            if (requestcode == CAMERA_CODE) {

                val photo =  data?.extras?.get("data") as Bitmap
                val stream = ByteArrayOutputStream()
                //Bitmap.CompressFormat agregar el formato desado, estoy usando aqui jpeg
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                //Agregamos al objecto album el arreglo de bytes
                imgArray =  stream.toByteArray()
                //Mostramos la imagen en la vista
                this.imageUI!!.setImageBitmap(photo)

                val bitmap = (imageUI!!.getDrawable() as BitmapDrawable).bitmap
            }

        }
    }

    //OBTENER ALBUMS
    private fun getAlbums(){
        val service: ApiService =  RestEngine.getRestEngine().create(ApiService::class.java)
        val result: Call<List<tbl_usuarios>> = service.getAlbums()

        result.enqueue(object: Callback<List<tbl_usuarios>>{

            override fun onFailure(call: Call<List<tbl_usuarios>>, t: Throwable){
                Toast.makeText(this@SingInActivity,"Error",Toast.LENGTH_LONG).show()
                println(t)
            }

            override fun onResponse(call: Call<List<tbl_usuarios>>, response: Response<List<tbl_usuarios>>){
                val arrayItems =  response.body()
                var strMessage:String =  ""
                if (arrayItems != null){
                    for (item in arrayItems!!){
                        strMessage =  strMessage + item.id_usuario.toString() +  " - " + item.nombre + "\n"
                        println("user: $strMessage")
                    }
                }

                //txtMessage!!.setText(strMessage)
                Toast.makeText(this@SingInActivity,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveAlbum(user: tbl_usuarios){

        val encodedString:String =  Base64.getEncoder().encodeToString(this.imgArray)

        val strEncodeImage:String = "data:image/png;base64," + encodedString

        user.avatar = strEncodeImage

        //SE CONSTRUYE EL OBJECTO A ENVIAR,  ESTO DEPENDE DE COMO CONSTRUYAS EL SERVICIO
        // SI TU SERVICIO POST REQUIERE DOS PARAMETROS HACER UN OBJECTO CON ESOS DOS PARAMETROS


        val service: ApiService =  RestEngine.getRestEngine().create(ApiService::class.java)
        val result: Call<Int> = service.saveAlbum(user)

        result.enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(this@SingInActivity,"error", Toast.LENGTH_LONG).show()
                println(t)
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                Toast.makeText(this@SingInActivity,"OK", Toast.LENGTH_LONG).show()
            }
        })
    }


}