package com.example.tvtraker.ui.fragments.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.tvtraker.R
import com.example.tvtraker.util.UserPreferences

class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {

    private val SPLASH_TIME: Long = 1800

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            val userPreferences = UserPreferences(requireContext())
            userPreferences.loginKey.asLiveData().observe(viewLifecycleOwner, {

                if (it == ""){
                    val action = SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment()
                    findNavController().navigate(action)
                }
                else{
                    val action = SplashScreenFragmentDirections.actionSplashScreenFragmentToSerijeFragment()
                    findNavController().navigate(action)
                }
            })
        },SPLASH_TIME)
    }
}