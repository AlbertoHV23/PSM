package com.psm.lmaddoubts.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.models.tbl_usuarios

class SingInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        val btn_login: Button = findViewById(R.id.btn_gologin)
        val btn_registrar: Button = findViewById(R.id.btn_registar)

        val txt_name: EditText = findViewById(R.id.txt_nameSign)
        val txt_apellidos: EditText = findViewById(R.id.txt_apellidos)
        val txt_loginEmail: EditText = findViewById(R.id.txt_loginEmail)
        val txt_loginPass: EditText = findViewById(R.id.txt_loginPass)


        btn_login.setOnClickListener(){
            onBackPressed()
        }

        btn_registrar.setOnClickListener(){
            var user:tbl_usuarios = tbl_usuarios(null,txt_name.text.toString(),txt_apellidos.text.toString(),txt_loginEmail.text.toString(),
                    txt_loginPass.text.toString(),"IMAGEN")

            println(user)
        }
    }

    private  fun showHome(){
        val intent: Intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)

    }
}