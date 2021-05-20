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
import androidx.appcompat.app.AlertDialog
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.Interface.RestEngine
import com.psm.lmaddoubts.Interface.UserService
import com.psm.lmaddoubts.models.tbl_usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*

class SingInActivity : AppCompatActivity() {
    var imageUI:ImageView? =  null
    var imgArray:ByteArray? =  null
    lateinit var USUARIOS:List<tbl_usuario>


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

       // getUsers()



        btn_login.setOnClickListener(){
            onBackPressed()
        }

        btn_registrar.setOnClickListener(){
            if (txt_name.text.toString().isEmpty() || txt_apellidos.text.toString().isEmpty()  || txt_loginEmail.text.toString().isEmpty()  ||txt_loginPass.text.toString().isEmpty() ){
                ShowAlert("Error", "Empty requirements")
            }
            else{
                var user:tbl_usuario = tbl_usuario(0,
                        txt_name.text.toString(),
                        txt_apellidos.text.toString(),
                        txt_loginEmail.text.toString(),
                        txt_loginPass.text.toString(),
                        null)
                saveUser(user)
            }



        }

        btn_opencam.setOnClickListener(){
            openCamera()
        }


    }

    companion object {

        //image pick code
        private val IMAGE_PICK_CODE = 100;
        //Permission code
        private val PERMISSION_CODE = 101;
        //camera code
        private val CAMERA_CODE = 102;
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


    //OBTENER USUARIOS
    private fun getUsers(){
        val service: UserService =  RestEngine.getRestEngine().create(UserService::class.java)
        val result: Call<List<tbl_usuario>> = service.getUsuarios()

        result.enqueue(object: Callback<List<tbl_usuario>> {

            override fun onFailure(call: Call<List<tbl_usuario>>, t: Throwable){
                Toast.makeText(this@SingInActivity,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<tbl_usuario>>, response: Response<List<tbl_usuario>>){
                val arrayItems =  response.body()
                if (arrayItems != null) {
                    USUARIOS = arrayItems
                }

                Toast.makeText(this@SingInActivity,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveUser(user:tbl_usuario){

        val encodedString:String =  Base64.getEncoder().encodeToString(this.imgArray)

        val strEncodeImage:String = "data:image/png;base64," + encodedString

        //SE CONSTRUYE EL OBJECTO A ENVIAR,  ESTO DEPENDE DE COMO CONSTRUYAS EL SERVICIO
        // SI TU SERVICIO POST REQUIERE DOS PARAMETROS HACER UN OBJECTO CON ESOS DOS PARAMETROS
        user.avatar = strEncodeImage


        val service: UserService =  RestEngine.getRestEngine().create(UserService::class.java)
        val result: Call<Int> = service.saveUsuarios(user)

        result.enqueue(object: Callback<Int>{
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(this@SingInActivity,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                Toast.makeText(this@SingInActivity,"OK",Toast.LENGTH_LONG).show()

                showHome(response.body().toString())

            }
        })
    }

    private  fun showHome(id_user:String){
        val intent:Intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("ID_USUARIO",id_user)
        startActivity(intent)
        finish()
    }








}