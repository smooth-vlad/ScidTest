package com.android.scidtest.ui.list.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import com.android.scidtest.databinding.FragmentListBinding
import com.android.scidtest.di.App
import com.android.scidtest.ui.list.adapter.BookItemsAdapter
import com.android.scidtest.ui.list.viewmodel.ListViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel

    private var _binding: FragmentListBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = App.getMainComponent(requireContext())
        val viewModelFactory = appComponent.getListViewModelFactory()
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[ListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = viewModel.items
        val adapter = BookItemsAdapter()
        binding.booksRecyclerView.adapter = adapter

        // submit data into recycler view, when it loads
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                items.collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        // hide shimmer and display actual contents
        adapter.addOnPagesUpdatedListener {
            // make sure to call this on first load only
            if (adapter.itemCount > 0 && binding.shimmerList.isShimmerVisible)
                onFirstDataLoaded()
        }

        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
            // auto-turn off swipe refresh after 750ms
            lifecycleScope.launch {
                // isn't blocking
                delay(750)
                binding.swipeRefresh.isRefreshing = false
            }
        }

        // Add vertical separator
        binding.booksRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun onFirstDataLoaded() {
        binding.shimmerList.apply {
            stopShimmer()
            visibility = View.GONE
        }
        binding.booksRecyclerView.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}