package com.psm.lmaddoubts.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.psm.lmaddoubts.Interface.RestEngine
import com.psm.lmaddoubts.Interface.UserService
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.models.UserAplication
import com.psm.lmaddoubts.models.UserAplication.Companion.pref
import com.psm.lmaddoubts.models.tbl_usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    lateinit var txt_email:EditText
    lateinit var txt_pass:EditText

    lateinit var email:String
    lateinit var password:String

     var USUARIOLOGEADO:List<tbl_usuario> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (pref.getIdUsuario().toString().isNotEmpty()){
            val intent:Intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("ID_USUARIO", pref.getIdUsuario())
            startActivity(intent)
            finish()

        }
        //BOTONOES
        val btn_activitySignIn:Button = findViewById(R.id.btn_activitySignIn)
        val btn_logear:Button = findViewById(R.id.btn_logear)
        //EDITTEXT
        txt_email = findViewById(R.id.txt_loginEmail)
        txt_pass = findViewById(R.id.txt_loginPass)

        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            pref.setWifi("True")


        }
        else {
            pref.setWifi("False")

        }



        btn_activitySignIn.setOnClickListener(){
            showignIn()
        }

        btn_logear.setOnClickListener() {
            var wifi = pref.getWifi()

            if (wifi !="False"){
                ValidarRegistro()
            }
            else{
                var i = UserAplication.dbHelper.getAlbum(txt_email.text.toString())
                val intent:Intent = Intent(this, HomeActivity::class.java)
                if (i != null) {
                    pref.saveIdUsuario(i.id_usuario.toString())
                    pref.saveNombre(i.nombre.toString())
                    pref.saveapellido(i.apellidos.toString())
                    pref.saveCorreo(i.email.toString())
                    pref.savePasseord(i.contrasena.toString())
                }

                startActivity(intent)
                finish()
            }


        }



    }



    private  fun showignIn(){
        val intent:Intent = Intent(this, SingInActivity::class.java)
        startActivity(intent)

    }

    private  fun showHome(id_user: String){
        val intent:Intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("ID_USUARIO", id_user)
        startActivity(intent)
        finish()
    }

    private fun ValidarRegistro(){
        if (txt_email.text.toString().isNotEmpty() || txt_pass.text.toString().isNotEmpty()){
            email = txt_email.text.toString()
            password = txt_pass.text.toString()
            UserLogin(email, password)


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



    private fun UserLogin(email: String, pass: String){

        var user= tbl_usuario(null, null, null, email, pass, null)



        val service: UserService =  RestEngine.getRestEngine().create(UserService::class.java)
        val result: Call<List<tbl_usuario>> = service.getUserLogeado(user)

        result.enqueue(object : Callback<List<tbl_usuario>> {


            override fun onFailure(call: Call<List<tbl_usuario>>, t: Throwable) {
                println("no $t")
            }

            override fun onResponse(
                call: Call<List<tbl_usuario>>,
                response: Response<List<tbl_usuario>>
            ) {

                USUARIOLOGEADO = response.body()!!
                if (USUARIOLOGEADO.isNotEmpty()) {

                    showHome(USUARIOLOGEADO.last().id_usuario.toString())
                } else {
                    ShowAlert("Error", "email or password not found")
                }


            }
        })

    }

    override fun onResume() {
        super.onResume()
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            pref.setWifi("True")


        }
        else {
            pref.setWifi("False")

        }

    }

}
