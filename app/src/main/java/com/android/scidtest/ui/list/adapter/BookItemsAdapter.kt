package com.android.scidtest.ui.list.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.android.scidtest.R
import com.android.scidtest.data.db.Book
import com.android.scidtest.databinding.ItemBookBinding
import com.android.scidtest.ui.details.view.DetailsFragment
import com.android.scidtest.ui.details.view.DetailsFragmentDirections
import com.android.scidtest.ui.list.view.ListFragmentDirections
import com.android.scidtest.ui.list.view.ListFragmentDirections.ActionFirstFragmentToSecondFragment

class BookItemsAdapter : PagingDataAdapter<Book, BookItemsAdapter.ViewHolder>(BOOK_DIFF_CALLBACK) {

    class ViewHolder(private val binding: ItemBookBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        val titleTextView = binding.titleTextView
    }

    companion object {
        private val BOOK_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { item ->
            holder.titleTextView.text = item.label
            holder.itemView.setOnClickListener {
                // open DetailsFragment and pass this book's id as 'bookId'
                // also pass new label to use in parent action bar
                val action =
                    ListFragmentDirections.actionFirstFragmentToSecondFragment(item.id, item.label)
                it.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
}