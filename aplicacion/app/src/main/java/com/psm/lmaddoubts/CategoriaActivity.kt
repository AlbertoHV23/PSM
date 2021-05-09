package com.psm.lmaddoubts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class CategoriaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categoria)

        var categoria=  intent.getStringExtra("CATEGORIA")

        var nameCa:TextView = findViewById(R.id.txt_ActividadCategoria)

        nameCa.text = categoria


    }
}