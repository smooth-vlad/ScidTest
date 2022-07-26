package com.android.scidtest.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.android.scidtest.data.db.Book
import com.android.scidtest.data.db.BooksDatabase
import com.android.scidtest.data.network.CandidateScidService
import com.android.scidtest.data.network.PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException

/**
 * Remote mediator is used for caching data in a local database.
 * It uses database as a main source of data, and makes network call if needed data doesn't exist in the database
 */
@OptIn(ExperimentalPagingApi::class)
class BooksRemoteMediator(
    private val database: BooksDatabase,
    private val networkService: CandidateScidService
) : RemoteMediator<Int, Book>() {
    companion object {
        const val TAG = "BooksRemoteMediator"
    }

    private val booksDao = database.bookDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Book>): MediatorResult {
        return try {
            Log.d(TAG, "loadType: ${loadType.name}")
            val pageToLoad: Int = when (loadType) {
                LoadType.REFRESH -> 1
                // in this case we never use prepend, so just exit with success message
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.pages.last().data.lastOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    val currentPage = lastItem.id / PAGE_SIZE
                    currentPage + 1
                }
            }

            Log.d(TAG, "request: page: $pageToLoad")
            val response = networkService.getBooks(pageToLoad)
            Log.d(
                TAG,
                "response: gotItems: ${response.result.data.size}, first item id: ${response.result.data[0].id}"
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    booksDao.clearAll()
                }

                booksDao.insertAll(response.result.data.map {
                    Book(
                        it.id,
                        it.name,
                        it.description,
                        it.date
                    )
                })
            }

            MediatorResult.Success(
                endOfPaginationReached = response.result.nextPageUrl == null
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}