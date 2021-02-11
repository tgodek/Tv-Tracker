package com.example.tvtraker.ui.fragments.register

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tvtraker.R
import com.example.tvtraker.databinding.FragmentRegisterBinding
import com.example.tvtraker.model.Korisnik
import com.example.tvtraker.repository.LoginRepository
import com.example.tvtraker.ui.fragments.login.LoginViewModel
import com.example.tvtraker.ui.fragments.login.LoginViewModelFactory
import com.example.tvtraker.util.enable
import com.example.tvtraker.util.visible
import com.example.tvtraker.webservice.Resource
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var viewModel: LoginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRegisterBinding.bind(view)
        binding.progressBarRegister.visible(false)
        binding.buttonRegister.enable(false)

        val repository = LoginRepository()
        val viewModelFactory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        viewModel.apiResponse2.observe(viewLifecycleOwner, { response ->
            when(response){
                is Resource.Success -> {
                    binding.progressBarRegister.visible(false)

                    if (response.value.message == "Korisik vec postoji"){
                        Snackbar.make(requireView(), "Email already exists", Snackbar.LENGTH_SHORT).show()
                    }
                    else if (response.value.message == "Registracija uspjesna"){
                        Snackbar.make(requireView(), "Successful registration", Snackbar.LENGTH_SHORT).show()
                        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                        findNavController().navigate(action)
                    }
                }
                is Resource.Loading -> {
                    binding.progressBarRegister.visible(true)
                }
                is Resource.Failure -> {
                    binding.progressBarRegister.visible(false)
                }
            }

        })

        binding.editTextRepeatPass.addTextChangedListener {
            val firstname = binding.editTextFirstname.text.toString().trim()
            val lastname = binding.editTextLastname.text.toString().trim()
            val username = binding.editTextUserName.text.toString().trim()
            val password = binding.editTextPass.text.toString().trim()
            val email = binding.editTextemail.text.toString().trim()

            if (firstname.isNotEmpty() && lastname.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() && it.toString().isNotEmpty()){
                binding.buttonRegister.enable(true)
            }
        }

        binding.buttonRegister.setOnClickListener {
            val firstname = binding.editTextFirstname.text.toString().trim()
            val lastname = binding.editTextLastname.text.toString().trim()
            val username = binding.editTextUserName.text.toString().trim()
            val password = binding.editTextPass.text.toString().trim()
            val email = binding.editTextemail.text.toString().trim()
            val korisnik = Korisnik("",firstname,lastname,username,password,email, emptyList())
            viewModel.registerKorisnik(korisnik)

        }
    }
}