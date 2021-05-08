package com.psm.lmaddoubts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SingInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        val btn_login: Button = findViewById(R.id.btn_gologin)

        btn_login.setOnClickListener(){
            onBackPressed()
        }
    }

    private  fun showHome(){
        val intent: Intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)

    }
}