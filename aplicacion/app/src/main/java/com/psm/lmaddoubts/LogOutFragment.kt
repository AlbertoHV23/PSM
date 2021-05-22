package com.psm.lmaddoubts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.psm.lmaddoubts.activities.LoginActivity
import com.psm.lmaddoubts.models.UserAplication.Companion.pref


class LogOutFragment : Fragment() {
    private var context2: Context? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root =  inflater.inflate(R.layout.fragment_log_out, container, false)

        var btn_log:Button = root.findViewById(R.id.btn_logout)
        println(pref.getIdUsuario())

        btn_log.setOnClickListener(){
            pref.wipe()
            val intent: Intent = Intent(context2, LoginActivity::class.java)


            startActivity(intent)


        }

        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context2 = context
    }


}