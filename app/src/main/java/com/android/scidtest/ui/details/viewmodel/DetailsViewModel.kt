package com.android.scidtest.ui.details.viewmodel

import androidx.lifecycle.*
import androidx.paging.*
import com.android.scidtest.data.db.BooksDatabase
import com.android.scidtest.data.network.CandidateScidService
import com.android.scidtest.data.network.classes.Book
import com.android.scidtest.data.repository.BooksRemoteMediator
import com.android.scidtest.data.repository.BooksRepository
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val booksRepository: BooksRepository,
    private val bookId: Int
) : ViewModel() {

    val item = MutableLiveData<com.android.scidtest.data.db.Book>()

    init {
        viewModelScope.launch {
            val book = booksRepository.getBook(bookId)
            if (book != null)
                item.postValue(book)
            else {
                // this will never happen in our case, because we are accessing book right after we have cached it }
            }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory(
    private val booksRepository: BooksRepository,
    private val bookId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(booksRepository, bookId) as T
    }
}