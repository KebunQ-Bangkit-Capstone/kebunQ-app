package com.md.kebunq.ui.listPenyakit

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.md.kebunq.R

class DetailPenyakitFragment : Fragment() {

    companion object {
        fun newInstance() = DetailPenyakitFragment()
    }

    private val viewModel: DetailPenyakitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_detail_penyakit, container, false)
    }
}