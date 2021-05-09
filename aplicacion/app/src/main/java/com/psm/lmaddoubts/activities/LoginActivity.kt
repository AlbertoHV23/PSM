package com.psm.lmaddoubts.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.models.Encriptacion

class LoginActivity : AppCompatActivity() {
    lateinit var txt_email:EditText
    lateinit var txt_pass:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //BOTONOES
        val btn_activitySignIn:Button = findViewById(R.id.btn_activitySignIn)
        val btn_logear:Button = findViewById(R.id.btn_logear)
        //EDITTEXT
        txt_email = findViewById(R.id.txt_loginEmail)
        txt_pass = findViewById(R.id.txt_loginPass)


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
}
