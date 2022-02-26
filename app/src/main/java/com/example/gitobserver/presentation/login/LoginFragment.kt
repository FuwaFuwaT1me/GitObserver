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
import com.example.gitobserver.domain.usecase.SharedPrefUserStorageUseCase
import com.example.gitobserver.utils.CheckInternetConnection
import com.example.gitobserver.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val mViewModel: LoginViewModel by viewModels()
    private val mBinding by viewBinding(FragmentLoginBinding::bind)

    @Inject
    lateinit var sharedPrefUserStorageUseCase: SharedPrefUserStorageUseCase

    private var isLoginByToken = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (sharedPrefUserStorageUseCase.getUserDetails() != null) {
            moveToRepositories(view)
        }
    }

    override fun onResume() {
        super.onResume()

        mBinding.toolBar.title = "GitHub observer"
        setupUI(requireView())
    }

    private fun setupUI(view: View) {
        mBinding.toolBar.title = "GitHub observer"

        mBinding.btnLogin.setOnClickListener {
            if (CheckInternetConnection.isInternetAvailable(requireContext())) {
                if (isLoginByToken) {
                    requestLoginWithToken(
                        view = view,
                        inputToken = mBinding.etLogin.text.toString()
                    )
                } else {
                    requestLoginWithUsername(
                        view = view,
                        inputUsername = mBinding.etLogin.text.toString()
                    )
                }
            } else {
                Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        mBinding.etLogin.addTextChangedListener {
            updateErrorMessageToken(isError = false)
            updateErrorMessageUsername(isError = false)
        }

        mBinding.btnChangeLoginType.setOnClickListener {
            if (isLoginByToken) {
                isLoginByToken = false
                mBinding.etLayoutLogin.hint = "Username"
                mBinding.btnChangeLoginType.text = "Login with token"
            } else {
                isLoginByToken = true
                mBinding.etLayoutLogin.hint = "Personal access token"
                mBinding.btnChangeLoginType.text = "Login with username"
            }
        }
    }

    private fun requestLoginWithToken(view: View, inputToken: String) {
        mViewModel.login(inputToken)
            .observe(viewLifecycleOwner) {
                Log.d("AAA", "login $it")
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            updateLoading(isLoading = false)
                            sharedPrefUserStorageUseCase.saveUserDetails(resource.data!!)
                            moveToRepositories(view)
                        }
                        Status.ERROR -> {
                            updateLoading(isLoading = false)
                            updateErrorMessageToken(isError = true)
                            Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                        Status.LOADING -> {
                            updateLoading(isLoading = true)
                        }
                    }
                }
            }
    }

    private fun requestLoginWithUsername(view: View, inputUsername: String) {
        mViewModel.loginWithUsername(inputUsername)
            .observe(viewLifecycleOwner) {
                Log.d("AAA", "login $it")
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            updateLoading(isLoading = false)
                            sharedPrefUserStorageUseCase.saveUserDetails(resource.data!!)
                            moveToRepositories(view)
                        }
                        Status.ERROR -> {
                            updateLoading(isLoading = false)
                            updateErrorMessageUsername(isError = true)
                            Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT)
                                .show()
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

    private fun updateErrorMessageToken(isError: Boolean) {
        if (isError) {
            mBinding.etLayoutLogin.error = "Invalid token"
        } else {
            mBinding.etLayoutLogin.error = ""
        }
    }

    private fun updateErrorMessageUsername(isError: Boolean) {
        if (isError) {
            mBinding.etLayoutLogin.error = "Invalid username"
        } else {
            mBinding.etLayoutLogin.error = ""
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
