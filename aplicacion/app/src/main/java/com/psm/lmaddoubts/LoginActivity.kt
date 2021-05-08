package com.psm.lmaddoubts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btn_activitySignIn:Button = findViewById(R.id.btn_activitySignIn)
        //val btn_activitySignIn:Button = findViewById(R.id.btn_activitySignIn)


        btn_activitySignIn.setOnClickListener(){
            showignIn()
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
}