package com.example.tvtraker.ui.fragments.profil

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvtraker.R
import com.example.tvtraker.adapter.SerijeAdapter
import com.example.tvtraker.databinding.FragmentProfilBinding
import com.example.tvtraker.model.TvSerija
import com.example.tvtraker.repository.SerijeRepository
import com.example.tvtraker.ui.fragments.series.SerijeViewModel
import com.example.tvtraker.ui.fragments.series.SerijeViewModelFactory
import com.example.tvtraker.util.UserPreferences
import com.example.tvtraker.util.visible
import com.example.tvtraker.webservice.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class ProfilFragment : Fragment(R.layout.fragment_profil) {

    private lateinit var viewModel: SerijeViewModel
    private lateinit var userPreferences: UserPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentProfilBinding.bind(view)

        binding.progressBarProfil.visible(false)

        userPreferences = UserPreferences(requireContext())
        val repository = SerijeRepository()
        val viewModelFactory = SerijeViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(SerijeViewModel::class.java)

        userPreferences.loginKey.asLiveData().observe(viewLifecycleOwner, {
            it?.let {
                viewModel.dodaneSerije(it)
                viewModel.dohvatiIme(it)
            }
        })

        val serijeAdapter = SerijeAdapter { tvSerija: TvSerija -> openTvSerijaDetailPrikaz(tvSerija) }

        binding.apply {
            val manager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)


            binding.recyclerViewUserSerije.apply {
                adapter = serijeAdapter
                layoutManager = manager
            }
        }

        viewModel.apiResponse3.observe(viewLifecycleOwner, { response ->
            when(response){
                is Resource.Success -> {
                    binding.progressBarProfil.visible(false)
                    serijeAdapter.submitList(response.value)
                }
                is Resource.Loading -> {
                    binding.progressBarProfil.visible(true)
                }
                is  Resource.Failure -> {
                    Snackbar.make(requireView(), "Server error", Snackbar.LENGTH_LONG).show()
                    binding.progressBarProfil.visible(false)
                }
            }

        })

        viewModel.apiResponse7.observe(viewLifecycleOwner, { response ->
            when(response){
                is Resource.Success -> {
                    activity?.toolbar?.title = response.value[0].kor_ime
                }
            }
        })
    }


    private fun openTvSerijaDetailPrikaz(tvSerija: TvSerija) {
        val action = ProfilFragmentDirections.actionProfilFragmentToProfilSerijeDetailFragment(tvSerija)
        findNavController().navigate(action)
    }
}