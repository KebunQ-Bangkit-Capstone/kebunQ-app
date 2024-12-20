package com.md.kebunq.ui.listPenyakit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.md.kebunq.data.response.DiseasesItem
import com.md.kebunq.data.retrofit.ApiConfig
import com.md.kebunq.databinding.FragmentDetailPenyakitBinding

@Suppress("DEPRECATION")
class DetailPenyakitFragment : Fragment() {

    private var _binding: FragmentDetailPenyakitBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailPenyakitViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        setHasOptionsMenu(true)
        _binding = FragmentDetailPenyakitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil data dari arguments
        val plantId = arguments?.getString("plant_id") ?: "0"
        val disease = arguments?.getParcelable<DiseasesItem>("disease_item")

        // Tampilkan data di UI
        if (disease != null) {
            binding.tvTitle.text = disease.diseaseName
            binding.tvDescription.text = disease.description
            Glide.with(this)
                .load(disease.temporaryImageUrl)
                .into(binding.ivPlant)
        }

        setupViewModel()

        // Panggil API jika data disease ada
        val diseaseId = disease?.diseaseId
        if (diseaseId != null) {
            viewModel.fetchDiseaseById(plantId, diseaseId)
        } else {
            Toast.makeText(context, "Data penyakit tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupViewModel() {
        val apiService = ApiConfig.getApiService() // Assuming ApiConfig is configured
        viewModel = ViewModelProvider(
            this,
            PenyakitViewModelFactory(apiService)
        )[DetailPenyakitViewModel::class.java]

        // Observers
//        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
//            message?.let { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
//        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.disease.observe(viewLifecycleOwner) { diseases ->
            if (diseases != null) {
                // Update UI with fetched data
                binding.tvTitle.text = diseases.analysis
                binding.tvDescription.text = diseases.description
                Glide.with(this)
                    .load(diseases.temporaryImageUrl)
                    .into(binding.ivPlant)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp() // Navigasi kembali
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
