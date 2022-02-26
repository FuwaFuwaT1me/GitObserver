package com.example.gitobserver.presentation.repositories

import android.os.Bundle
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_repositories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        updateRepositories()
    }

    private fun setupUI() {
        mBinding.toolBar.title = "Repositories"

        mBinding.toolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.exit -> {
                    view?.findNavController()?.popBackStack()
                    sharedPrefUserStorageUseCase.exitUser()
                    true
                }
                else -> false
            }
        }
    }

    private fun updateRepositories() {
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
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        mBinding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun updateRecycler(repositories: List<GitHubRepository>) {
        mAdapter = RepositoriesRecyclerAdapter(repositories, sharedPrefLanguageColorsStorageUseCase, this)
        mBinding.rcvRepositories.adapter = mAdapter
        mAdapter.notifyDataSetChanged()
    }

    override fun onclick(gitHubRepository: GitHubRepository) {
        val action: RepositoriesFragmentDirections.ActionRepositoriesFragmentToContentsFragment =
            RepositoriesFragmentDirections.actionRepositoriesFragmentToContentsFragment(gitHubRepository)
        action.repository = gitHubRepository
        view?.findNavController()?.navigate(action)
    }
}
