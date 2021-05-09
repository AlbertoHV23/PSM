package com.psm.lmaddoubts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast

class CreatePostActivity : AppCompatActivity() {
    var materia:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        val spinner: Spinner = findViewById(R.id.spinner)

        var lista = resources.getStringArray(R.array.Categorias)
        var adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,lista)
        spinner.adapter = adapter


        spinner.onItemSelectedListener = object:
        AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               materia = lista[position]
                println(materia)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }





    }
}