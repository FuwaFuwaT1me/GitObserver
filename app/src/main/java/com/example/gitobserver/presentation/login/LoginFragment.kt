package com.example.gitobserver.presentation.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.gitobserver.R
import com.example.gitobserver.databinding.FragmentLoginBinding
import com.example.gitobserver.domain.usecase.SharedPrefStorageUseCase
import com.example.gitobserver.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val mViewModel: LoginViewModel by viewModels()
    private val mBinding by viewBinding(FragmentLoginBinding::bind)

    @Inject
    lateinit var sharedPrefStorageUseCase: SharedPrefStorageUseCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI(view)
    }

    private fun setupUI(view: View) {
        mBinding.btnLogin.setOnClickListener {
            requestLogin(view = view, inputToken = mBinding.etLoginToken.text.toString())
        }

        mBinding.etLoginToken.addTextChangedListener {
            Log.d("AAA", "changed")
            updateErrorMessage(isError = false)
        }
    }

    private fun requestLogin(view: View, inputToken: String) {
        mViewModel.login(inputToken)
            .observe(viewLifecycleOwner) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            updateLoading(isLoading = false)
                            sharedPrefStorageUseCase.saveUserDetails(resource.data!!)
                            moveToRepositories(view)
                        }
                        Status.ERROR -> {
                            updateLoading(isLoading = false)
                            updateErrorMessage(isError = true)
                            Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                        }
                        Status.LOADING -> {
                            updateLoading(isLoading = true)
                        }
                    }
                }
            }
    }

    private fun moveToRepositories(view: View) {
        view.findNavController().navigate(R.id.action_loginFragment_to_repositoriesFragment)
    }

    private fun updateErrorMessage(isError: Boolean) {
        if (isError) {
            mBinding.etLayoutLoginToken.error = "Invalid token"
        } else {
            mBinding.etLayoutLoginToken.error = ""
        }
    }

    private fun updateLoading(isLoading: Boolean) {
        if (isLoading) {
            mBinding.progress.visibility = View.VISIBLE
            mBinding.btnLogin.text = ""
        } else {
            mBinding.progress.visibility = View.INVISIBLE
            mBinding.btnLogin.text = "Sign in"
        }
    }
}
