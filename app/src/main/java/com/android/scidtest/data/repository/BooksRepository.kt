package com.android.scidtest.data.repository

import androidx.paging.PagingSource
import com.android.scidtest.data.db.Book
import com.android.scidtest.data.db.BooksDatabase
import com.android.scidtest.data.network.CandidateScidService

class BooksRepository(
    private val booksDatabase: BooksDatabase,
    private val candidateScidService: CandidateScidService
) {
    /**
     * Get books remote mediator
     * @return BooksRemoteMediator, that is using booksDatabase and candidateScidService for network
     */
    fun getBooksRemoteMediator(): BooksRemoteMediator {
        return BooksRemoteMediator(booksDatabase, candidateScidService)
    }

    /**
     * Get books paging source
     * @return PagingSource with <Id, Book> key-vals
     */
    fun getPagingSource(): PagingSource<Int, Book> {
        return booksDatabase.bookDao().pagingSource()
    }

    /**
     * Get book by it's id
     * @return Book or null if there is no book with this id
     */
    suspend fun getBook(id: Int): Book? {
        val dao = booksDatabase.bookDao()
        return dao.getById(id)
    }
}