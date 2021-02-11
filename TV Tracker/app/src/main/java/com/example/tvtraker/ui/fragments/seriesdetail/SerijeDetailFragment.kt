package com.example.tvtraker.ui.fragments.seriesdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.tvtraker.R
import com.example.tvtraker.databinding.FragmentSerijeDetailBinding
import com.example.tvtraker.repository.SerijeRepository
import com.example.tvtraker.ui.fragments.series.SerijeViewModel
import com.example.tvtraker.ui.fragments.series.SerijeViewModelFactory
import com.example.tvtraker.util.UserPreferences
import com.example.tvtraker.util.visible
import com.example.tvtraker.webservice.Resource
import com.google.android.material.snackbar.Snackbar

class SerijeDetailFragment : Fragment(R.layout.fragment_serije_detail) {

    private val args: SerijeDetailFragmentArgs by navArgs()
    private lateinit var viewModel: SerijeViewModel
    private lateinit var userPreferences: UserPreferences
    private var user = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSerijeDetailBinding.bind(view)

        userPreferences = UserPreferences(requireContext())
        val repository = SerijeRepository()
        val viewModelFactory = SerijeViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(SerijeViewModel::class.java)

        binding.progressbarSerijeDetails.visible(false)
        binding.txtTitle.text = args.Serija.naslov
        binding.textViewDetail.text = args.Serija.opis
        binding.textViewGlumci.text = args.Serija.glumci
        var zanrovi = ""
        args.Serija.zanr.forEach {
            zanrovi += it.naziv + " "
        }
        binding.textViewZanr.text = zanrovi
        Glide.with(this)
            .load(args.Serija.poster)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imageViewPoster)

        userPreferences.loginKey.asLiveData().observe(viewLifecycleOwner,{
            it?.let {
                user = it
            }
        })

        viewModel.apiResponse2.observe(viewLifecycleOwner,{ response ->
            when(response){
                is Resource.Success -> {
                    binding.progressbarSerijeDetails.visible(false)
                    if (response.value.message == "Dodana nova serija"){
                        val action = SerijeDetailFragmentDirections.actionSerijeDetailFragmentToSerijeFragment()
                        findNavController().navigate(action)
                    }
                    else{
                        Snackbar.make(requireView(), "Greska sa serverom", Snackbar.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    binding.progressbarSerijeDetails.visible(true)
                }
                is Resource.Failure -> {
                    binding.progressbarSerijeDetails.visible(false)
                }
            }

        })

        binding.buttonAddSeriju.setOnClickListener {
            viewModel.dodajSeriju(args.Serija._id,user)
        }
    }
}