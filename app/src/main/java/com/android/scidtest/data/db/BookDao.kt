package com.android.scidtest.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(books: List<Book>)

    @Query("SELECT * FROM books")
    fun pagingSource(): PagingSource<Int, Book>

    @Query("DELETE FROM books")
    suspend fun clearAll()

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getById(bookId: Int): Book?
}