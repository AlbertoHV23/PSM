package com.psm.lmaddoubts.ui.Published

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.models.prefs
import com.psm.lmaddoubts.models.sharedPreferences.Companion.pref

class PublishedFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var root =  inflater.inflate(R.layout.fragment_published, container, false)

        var id = pref.getName()
        println(id)


        return root
    }

    }