package com.psm.lmaddoubts.ui.Published

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.psm.lmaddoubts.R

class PublishedFragment : Fragment() {

    private lateinit var galleryViewModel: PublishedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(PublishedViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_published, container, false)
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        return root
    }
}