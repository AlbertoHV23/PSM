package com.psm.lmaddoubts.activities

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.psm.lmaddoubts.Interface.ImageUtilities
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.Interface.RestEngine
import com.psm.lmaddoubts.Interface.UserService
import com.psm.lmaddoubts.models.prefs
import com.psm.lmaddoubts.models.sharedPreferences.Companion.pref
import com.psm.lmaddoubts.models.tbl_usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    var id_usuario_int = 0
    lateinit var USUARIOS:tbl_usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var id_user = intent.getStringExtra("ID_USUARIO")
        if (id_user != null) {
            id_usuario_int = id_user.toInt()
            getUser(id_usuario_int)
        }

        pref.saveName(id_user.toString())



       // println(pref.getName().toString())

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
                setOf(
                        R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
                ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)






    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        val names = findViewById<TextView>(R.id.nav_username)
        val emails = findViewById<TextView>(R.id.nav_emails)
        var imgarray:ImageView =  findViewById(R.id.imageView3)
        names.text = "${USUARIOS.nombre}  ${USUARIOS.apellidos}"
        emails.text = "${USUARIOS.email}  "
        println(USUARIOS.avatar)
        var byteArray =  Base64.getDecoder().decode(USUARIOS.avatar)
        imgarray!!.setImageBitmap(ImageUtilities.getBitMapFromByteArray(byteArray))

        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun getUser(id: Int){
        val service: UserService =  RestEngine.getRestEngine().create(UserService::class.java)
        val result: Call<List<tbl_usuario>> = service.getUsuarioId(id)

        result.enqueue(object : Callback<List<tbl_usuario>> {
            override fun onFailure(call: Call<List<tbl_usuario>>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Error", Toast.LENGTH_LONG).show()
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<List<tbl_usuario>>, response: Response<List<tbl_usuario>>) {
                var strMessage: String = ""
                var byteArray: ByteArray? = null
                val item = response.body()
                if (item != null) {
                    USUARIOS = item.last()
                    val strImage:String =  item[0].avatar!!.replace("data:image/png;base64,","")
                    item[0].avatar = strImage


                }

                println(USUARIOS)

                Toast.makeText(this@HomeActivity, "OK", Toast.LENGTH_LONG).show()
            }

        })

    }


}