package com.example.gitobserver.presentation.contents

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.gitobserver.R
import com.example.gitobserver.databinding.FragmentContentsBinding
import com.example.gitobserver.domain.model.GitHubRepository
import com.example.gitobserver.domain.usecase.SharedPrefUserStorageUseCase
import com.example.gitobserver.utils.CheckInternetConnection
import com.example.gitobserver.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import javax.inject.Inject


@AndroidEntryPoint
class ContentsFragment : Fragment() {

    private val mBinding by viewBinding(FragmentContentsBinding::bind)
    private val mViewModel: ContentsViewModel by viewModels()
    private val args by navArgs<ContentsFragmentArgs>()

    @Inject
    lateinit var sharedPrefUserStorageUseCase: SharedPrefUserStorageUseCase

    private lateinit var gitHubRepository: GitHubRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contents, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.fabRefresh.setOnClickListener {
            mBinding.ivNoInternet.visibility = View.INVISIBLE
            mBinding.fabRefresh.visibility = View.GONE
            updateRepositoryContent()
        }

        gitHubRepository = args.repository

        mBinding.toolBar.title = gitHubRepository.name
        mBinding.toolBar.setNavigationOnClickListener {
            view.findNavController().popBackStack()
        }
        mBinding.toolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.exit -> {
                    view.findNavController().navigate(R.id.action_contentsFragment_to_loginFragment)
                    sharedPrefUserStorageUseCase.exitUser()
                    true
                }
                else -> false
            }
        }
        updateRepositoryContent()
    }

    private fun setupUI(htmlContent: String = "") {
        mBinding.tvRepositoryUrl.text = gitHubRepository.url
        mBinding.tvRepositoryUrl.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(gitHubRepository.url))
            startActivity(browserIntent)
        }
        if (gitHubRepository.license == null) {
            mBinding.tvLicense.visibility = View.INVISIBLE
            mBinding.tvLicenseKey.visibility = View.INVISIBLE
        } else {
            mBinding.tvLicense.visibility = View.VISIBLE
            mBinding.tvLicenseKey.visibility = View.VISIBLE
            mBinding.tvLicenseKey.text = gitHubRepository.license?.key
        }
        mBinding.tvStars.text = gitHubRepository.starsCount.toString()
        mBinding.tvForks.text = gitHubRepository.forksCount.toString()
        mBinding.tvWatchers.text = gitHubRepository.watchersCount.toString()

        if (htmlContent == "/") {

        } else if (htmlContent == "") {
            mBinding.ivNothing.visibility = View.VISIBLE
        } else {
            val markwon = Markwon.create(requireContext())
            markwon.setMarkdown(mBinding.tvRepositoryContents, htmlContent)
        }
    }

    private fun updateRepositoryContent() {
        mViewModel.getRepositoryContent(
            username = gitHubRepository.owner.login,
            repository = gitHubRepository.name
        ).observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        mBinding.progressBar.visibility = View.GONE
                        mBinding.scrollViewRepository.visibility = View.VISIBLE
                        setupUI(resource.data ?: "")
                    }
                    Status.ERROR -> {
                        mBinding.progressBar.visibility = View.GONE
                        mBinding.scrollViewRepository.visibility = View.VISIBLE
                        if (!CheckInternetConnection.isInternetAvailable(requireContext())) {
                            mBinding.fabRefresh.visibility = View.VISIBLE
                            mBinding.ivNoInternet.visibility = View.VISIBLE
                            setupUI("/")
                        } else {
                            setupUI()
                        }
                    }
                    Status.LOADING -> {
                        mBinding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}