package com.android.scidtest.ui.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.android.scidtest.data.db.BooksDatabase
import com.android.scidtest.data.network.CandidateScidService
import com.android.scidtest.data.network.PAGE_SIZE
import com.android.scidtest.data.network.classes.Book
import com.android.scidtest.data.repository.BooksRemoteMediator
import com.android.scidtest.data.repository.BooksRepository

class ListViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val pager = Pager(
        config = PagingConfig(PAGE_SIZE, enablePlaceholders = false),
        remoteMediator = booksRepository.getBooksRemoteMediator()
    ) {
        booksRepository.getPagingSource()
    }
    val items = pager.flow.cachedIn(viewModelScope)
}

@Suppress("UNCHECKED_CAST")
class ListViewModelFactory(
    private val booksRepository: BooksRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListViewModel(booksRepository) as T
    }
}