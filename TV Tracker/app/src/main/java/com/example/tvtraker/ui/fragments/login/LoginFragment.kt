package com.example.tvtraker.ui.fragments.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tvtraker.R
import com.example.tvtraker.repository.LoginRepository
import com.example.tvtraker.util.UserPreferences
import com.example.tvtraker.util.visible
import com.example.tvtraker.webservice.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var viewModel: LoginViewModel
    private lateinit var userPreferences: UserPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarLogin.visible(false)

        userPreferences = UserPreferences(requireContext())
        val repository = LoginRepository()
        val viewModelFactory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        viewModel.apiResponse.observe(viewLifecycleOwner, {response ->
            when(response){
                is Resource.Success -> {
                    Log.d("login", response.value.toString())
                    progressBarLogin.visible(false)

                    if (response.value.message == "Krivi unos"){
                        Snackbar.make(requireView(), "Wrong username or password", Snackbar.LENGTH_LONG).show()
                    }
                    else if (response.value.message == "Greska"){
                        Snackbar.make(requireView(), "Server error", Snackbar.LENGTH_LONG).show()
                    }
                    else{
                        lifecycleScope.launch {
                            userPreferences.saveLoginKey(response.value.message)
                        }
                        val action = LoginFragmentDirections.actionLoginFragmentToSerijeFragment()
                        findNavController().navigate(action)
                    }

                }
                is Resource.Loading -> {
                    progressBarLogin.visible(true)
                }
                is  Resource.Failure -> {
                    progressBarLogin.visible(false)
                }
            }
        })

        btnLogin.setOnClickListener {
            val email = editTextUsername.text.toString().trim()
            val lozinka = editTextPassword.text.toString().trim()
            viewModel.dohvatiKorisnika(email, lozinka)
        }

        btnRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }

    }
}