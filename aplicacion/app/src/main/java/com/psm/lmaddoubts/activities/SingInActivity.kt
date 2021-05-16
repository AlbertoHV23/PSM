package com.psm.lmaddoubts.activities

import android.app.Activity
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
import com.psm.lmaddoubts.RestEngine
import com.psm.lmaddoubts.Service
import com.psm.lmaddoubts.models.tbl_usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*

class SingInActivity : AppCompatActivity() {
    var txtId: TextView? = null

    var imageUI:ImageView? =  null
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


        btn_login.setOnClickListener(){
            onBackPressed()
        }

        btn_registrar.setOnClickListener(){
            if (txt_name.text.toString().isEmpty() || txt_apellidos.text.toString().isEmpty()  || txt_loginEmail.text.toString().isEmpty()  ||txt_loginPass.text.toString().isEmpty() ){

            }
            else{
                var user:tbl_usuario = tbl_usuario(0,
                        txt_name.text.toString(),
                        txt_apellidos.text.toString(),
                        txt_loginEmail.text.toString(),
                        txt_loginPass.text.toString(),
                        null)
                    saveAlbum(user)
            }



        }

        btn_opencam.setOnClickListener(){
            openCamera()
        }


    }

    companion object {

        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
        //camera code
        private val CAMERA_CODE = 1002;
    }

    private fun validarRegistro(){
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveAlbum(user:tbl_usuario){

        val encodedString:String =  Base64.getEncoder().encodeToString(this.imgArray)

        val strEncodeImage:String = "data:image/png;base64," + encodedString

        //SE CONSTRUYE EL OBJECTO A ENVIAR,  ESTO DEPENDE DE COMO CONSTRUYAS EL SERVICIO
        // SI TU SERVICIO POST REQUIERE DOS PARAMETROS HACER UN OBJECTO CON ESOS DOS PARAMETROS
        user.avatar = strEncodeImage

        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<Int> = service.saveAlbum(user)

        result.enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(this@SingInActivity,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                Toast.makeText(this@SingInActivity,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }




}