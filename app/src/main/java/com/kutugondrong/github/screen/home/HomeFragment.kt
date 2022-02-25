package com.kutugondrong.github.screen.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.kutugondrong.data.model.User
import com.kutugondrong.github.MainActivity
import com.kutugondrong.github.R
import com.kutugondrong.github.databinding.HomeFragmentBinding
import com.kutugondrong.github.databinding.SearchViewBinding
import com.kutugondrong.github.utils.RecyclerViewItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {

    private var _binding: HomeFragmentBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    val adapter =
        UserItemAdapter { data: User -> goToDetailUser(data) }

    private fun goToDetailUser(data: User) {
        val directions = HomeFragmentDirections.actionHomeFragmentToDetailUserFragment(data)
        findNavController().navigate(directions)
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.showToolbar(false)
        (activity as? MainActivity)?.showHomeAsUpEnabled(false)
        viewModel.searchUser(HomeViewModel.DEFAULT_QUERY)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = HomeFragmentBinding.bind(view)
        binding.apply {
            recycleUserGithub.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                addItemDecoration(RecyclerViewItemDecoration())
            }
            recycleUserGithub.adapter = adapter.withLoadStateFooter(
                footer = UserLoadingStateAdapter { retry() }
            )

            swipeRefreshLayout.setOnRefreshListener {
                adapter.refresh()
            }

            SearchViewBinding.bind(view).apply {
                txtSearch.doAfterTextChanged {
                    it?.let {
                        recycleUserGithub.scrollToPosition(0)
                        viewModel.searchUser(it.toString())
                    }
                }
            }
        }
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressHome.isVisible = loadState.source.refresh is LoadState.Loading
                swipeRefreshLayout.isRefreshing = false
            }
        }

        viewModel.users.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

    }

    private fun retry() {
        adapter.retry()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
