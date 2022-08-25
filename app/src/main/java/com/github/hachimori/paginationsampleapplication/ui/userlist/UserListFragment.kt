package com.github.hachimori.paginationsampleapplication.ui.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.hachimori.paginationsampleapplication.databinding.FragmentUserlistBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private val userListViewModel: UserListViewModel by viewModels()

    private var _binding: FragmentUserlistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindAdapter()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindAdapter() {
        with(binding.userList) {
            adapter = UserAdapter()
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, VERTICAL)
            )
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            userListViewModel.pagingDataFlow.collectLatest { pagingData ->
                (binding.userList.adapter as UserAdapter).submitData(pagingData)
            }
        }
    }
}
