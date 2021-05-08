package com.psm.lmaddoubts.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.psm.lmaddoubts.R
import com.psm.lmaddoubts.adadpters.CategoriesAdapter

class HomeFragment : Fragment() {

    private var context2: Context? = null
    private var adapter: CategoriesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var root =  inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context2 = context
    }

}