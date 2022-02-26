package com.example.gitobserver.presentation.repositories

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.gitobserver.R
import com.example.gitobserver.databinding.FragmentRepositoriesBinding
import com.example.gitobserver.domain.model.GitHubRepository
import com.example.gitobserver.domain.usecase.SharedPrefLanguageColorsStorageUseCase
import com.example.gitobserver.domain.usecase.SharedPrefUserStorageUseCase
import com.example.gitobserver.presentation.repositories.adapter.RepositoriesRecyclerAdapter
import com.example.gitobserver.utils.CheckInternetConnection
import com.example.gitobserver.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RepositoriesFragment : Fragment(), OnRepositoryClickListener {

    @Inject
    lateinit var sharedPrefLanguageColorsStorageUseCase: SharedPrefLanguageColorsStorageUseCase

    @Inject
    lateinit var sharedPrefUserStorageUseCase: SharedPrefUserStorageUseCase

    private val mViewModel: RepositoriesViewModel by viewModels()
    private val mBinding by viewBinding(FragmentRepositoriesBinding::bind)

    private lateinit var mAdapter: RepositoriesRecyclerAdapter

    private var lastRepositories: List<GitHubRepository>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_repositories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.fabRefresh.setOnClickListener {
            mBinding.ivNothing.visibility = View.INVISIBLE
            mBinding.fabRefresh.visibility = View.INVISIBLE
            mBinding.ivNoInternet.visibility = View.INVISIBLE
            mBinding.ivError.visibility = View.INVISIBLE
            updateUI()
        }
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        mBinding.progressBar.visibility = View.VISIBLE
        if (CheckInternetConnection.isInternetAvailable(requireContext()) || lastRepositories != null) {
            mBinding.ivNoInternet.visibility = View.INVISIBLE
            mBinding.fabRefresh.visibility = View.GONE
            setupUI()
            updateRepositories()
        } else {
            mBinding.progressBar.visibility = View.GONE
            mBinding.ivNoInternet.visibility = View.VISIBLE
            mBinding.fabRefresh.visibility = View.VISIBLE
            Toast.makeText(requireContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupUI() {
        mBinding.toolBar.title = getString(R.string.repositories)

        mBinding.toolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.exit -> {
                    view?.findNavController()?.navigate(R.id.action_repositoriesFragment_to_loginFragment)
                    sharedPrefUserStorageUseCase.exitUser()
                    true
                }
                else -> false
            }
        }
    }

    private fun updateRepositories() {
        if (lastRepositories != null) {
            updateRecycler(lastRepositories!!)
        } else {
            mViewModel.getGitHubRepositories().observe(viewLifecycleOwner) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            updateRecycler(resource.data!!)
                            mBinding.progressBar.visibility = View.GONE
                            mBinding.rcvRepositories.visibility = View.VISIBLE
                        }
                        Status.ERROR -> {
                            mBinding.progressBar.visibility = View.GONE
                            mBinding.ivError.visibility = View.VISIBLE
                            mBinding.fabRefresh.visibility = View.VISIBLE
                        }
                        Status.LOADING -> {
                            mBinding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun updateRecycler(repositories: List<GitHubRepository>) {
        if (repositories.isEmpty()) {
            showIfEmpty()
        } else {
            mBinding.rcvRepositories.visibility = View.VISIBLE
            mBinding.progressBar.visibility = View.INVISIBLE
            lastRepositories = repositories
            mAdapter = RepositoriesRecyclerAdapter(
                repositories,
                sharedPrefLanguageColorsStorageUseCase,
                this
            )
            mBinding.rcvRepositories.adapter = mAdapter
            mAdapter.notifyDataSetChanged()
        }
    }

    private fun showIfEmpty() {
        mBinding.rcvRepositories.visibility = View.GONE
        mBinding.ivNothing.visibility = View.VISIBLE
        mBinding.fabRefresh.visibility = View.VISIBLE
    }

    override fun onclick(gitHubRepository: GitHubRepository) {
        val action: RepositoriesFragmentDirections.ActionRepositoriesFragmentToContentsFragment =
            RepositoriesFragmentDirections.actionRepositoriesFragmentToContentsFragment(
                gitHubRepository
            )
        action.repository = gitHubRepository
        view?.findNavController()?.navigate(action)
    }
}
