package ru.netology.nmedia

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.constant.ImageProvider
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaType
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.databinding.SignUpFragmentBinding
import ru.netology.nmedia.viewmodel.SignUpViewModel

class SignUpFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SignUpFragmentBinding.inflate(inflater, container, false)

        val viewModel: SignUpViewModel by viewModels()

        viewModel.data.observe(viewLifecycleOwner, {
            AppAuth.getInstance().setAuth(
                it.id,
                it.token
            )
            findNavController().navigateUp()
        })

        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            if (state.error)
                binding.repeatPasswordField.error = getString(R.string.error_pass_mismatch)

        }


        binding.signUpButton.setOnClickListener {
            if (binding.passwordField.editText?.text.toString() == binding.repeatPasswordField.editText?.text.toString()) {
                viewModel.registrationUser(
                    binding.loginField.editText?.text.toString(),
                    binding.passwordField.editText?.text.toString(),
                    binding.nameField.editText?.text.toString()
                    )
            } else binding.repeatPasswordField.error = getString(R.string.error_pass_mismatch)

        }

        return binding.root
    }
}