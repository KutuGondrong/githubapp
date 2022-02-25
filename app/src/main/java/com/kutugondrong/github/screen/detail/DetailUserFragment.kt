package com.kutugondrong.github.screen.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.kutugondrong.github.R
import com.kutugondrong.github.databinding.DetailUserFragmentBinding
import com.kutugondrong.github.utils.RecyclerViewItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailUserFragment : Fragment(R.layout.detail_user_fragment) {

    private val viewModel: DetailUserViewModel by viewModels()

    private val args: DetailUserFragmentArgs by navArgs()

    private val headerAdapter by lazy {
        HeaderUserDetailAdapter(args.user)
    }

    private val repoAdapter by lazy {
        RepoItemAdapter(args.user)
    }

    private val footerAdapter = RepoLoadingStateAdapter { retry() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DetailUserFragmentBinding.bind(view)
        binding.apply {
            recycleDetailUser.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                addItemDecoration(RecyclerViewItemDecoration())
            }


            val listOfAdapters = listOf(headerAdapter, repoAdapter.withLoadStateFooter(
                footer = footerAdapter
            ))

            recycleDetailUser.adapter = ConcatAdapter(listOfAdapters)

            repoAdapter.addLoadStateListener { loadState ->
                binding.apply {
                    progressDetailUser.isVisible = loadState.source.refresh is LoadState.Loading
                    swipeRefreshLayout.isRefreshing = false
                }
            }

            viewModel.getRepos(args.user)

            viewModel.repos.observe(viewLifecycleOwner) {
                it?.let {
                    repoAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                }
            }

            swipeRefreshLayout.setOnRefreshListener {
                repoAdapter.refresh()
            }

        }

    }

    private fun retry() {
        repoAdapter.retry()
    }

}
