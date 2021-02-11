package com.example.tvtraker.ui.fragments.series

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvtraker.R
import com.example.tvtraker.adapter.SerijeAdapter
import com.example.tvtraker.databinding.FragmentSerijeBinding
import com.example.tvtraker.model.TvSerija
import com.example.tvtraker.repository.SerijeRepository
import com.example.tvtraker.util.UserPreferences
import com.example.tvtraker.util.visible
import com.example.tvtraker.webservice.Resource
import com.google.android.material.snackbar.Snackbar

class SerijeFragment : Fragment(R.layout.fragment_serije) {

    private lateinit var viewModel: SerijeViewModel
    private lateinit var userPreferences: UserPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSerijeBinding.bind(view)
        binding.textView2.visible(false)
        binding.textView4.visible(false)
        binding.textView5.visible(false)
        binding.view.visible(false)
        binding.view2.visible(false)
        binding.view3.visible(false)
        binding.progressBarSerije.visible(false)

        userPreferences = UserPreferences(requireContext())
        val repository = SerijeRepository()
        val viewModelFactory = SerijeViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(SerijeViewModel::class.java)

        userPreferences.loginKey.asLiveData().observe(viewLifecycleOwner,{
            it?.let {
                viewModel.sveSerije(it)
                viewModel.trendingSerije(it)
                viewModel.latestSerije(it)
            }
        })

        val serijeAdapter = SerijeAdapter { tvSerija: TvSerija -> openTvSerijaDetailPrikaz(tvSerija) }
        val serijeAdapter2 = SerijeAdapter { tvSerija: TvSerija -> openTvSerijaDetailPrikaz(tvSerija) }
        val serijeAdapter3 = SerijeAdapter { tvSerija: TvSerija -> openTvSerijaDetailPrikaz(tvSerija) }

        binding.apply {
            val mLinearLayoutManager = LinearLayoutManager(requireContext())
            mLinearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

            val mLinearLayoutManager2 = LinearLayoutManager(requireContext())
            mLinearLayoutManager2.orientation = LinearLayoutManager.HORIZONTAL

            val mLinearLayoutManager3 = LinearLayoutManager(requireContext())
            mLinearLayoutManager3.orientation = LinearLayoutManager.HORIZONTAL

            recyclerViewSerije.apply {
                adapter = serijeAdapter
                layoutManager = mLinearLayoutManager
            }

            recyclerViewSerijeTrending.apply {
                adapter = serijeAdapter2
                layoutManager = mLinearLayoutManager2
            }

            recyclerViewSerijeLatest.apply {
                adapter = serijeAdapter3
                layoutManager = mLinearLayoutManager3
            }
        }

        //prva lista serija
        viewModel.apiResponse.observe(viewLifecycleOwner,{ response ->
            when(response){
                is Resource.Success -> {
                    binding.progressBarSerije.visible(false)
                    binding.textView2.visible(true)
                    binding.view.visible(true)
                    serijeAdapter.submitList(response.value)
                }
                is Resource.Loading -> {
                    binding.progressBarSerije.visible(true)
                }
                is Resource.Failure -> {
                    binding.progressBarSerije.visible(false)
                    Snackbar.make(requireView(), "Server error", Snackbar.LENGTH_LONG).show()
                }
            }
        })

        //druga lista serija
        viewModel.apiResponse5.observe(viewLifecycleOwner, { response ->
            when(response){
                is  Resource.Success -> {
                    binding.textView4.visible(true)
                    binding.view2.visible(true)
                    serijeAdapter2.submitList(response.value)
                }
                is Resource.Loading -> { }
                is Resource.Failure -> {
                    Snackbar.make(requireView(), "Server error", Snackbar.LENGTH_LONG).show()
                }
            }
        })

        //treca lista serija
        viewModel.apiResponse6.observe(viewLifecycleOwner, { response ->
            when(response){
                is Resource.Success -> {
                    binding.view3.visible(true)
                    binding.textView5.visible(true)
                    serijeAdapter3.submitList(response.value)
                }
                is Resource.Loading -> {}
                is Resource.Failure -> {
                    Snackbar.make(requireView(), "Server error", Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun openTvSerijaDetailPrikaz(tvSerija: TvSerija) {
        val action = SerijeFragmentDirections.actionSerijeFragmentToSerijeDetailFragment(tvSerija)
        findNavController().navigate(action)
    }
}