package com.psm.lmaddoubts.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class SingInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)
    }

    private  fun showHome(){
        val intent: Intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)

    }
}