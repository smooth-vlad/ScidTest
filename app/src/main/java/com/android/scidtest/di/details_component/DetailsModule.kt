package com.android.scidtest.di.details_component

import android.content.Context
import androidx.room.Room
import com.android.scidtest.data.db.BooksDatabase
import com.android.scidtest.data.network.CandidateScidService
import com.android.scidtest.data.repository.BooksRepository
import com.android.scidtest.ui.details.viewmodel.DetailsViewModelFactory
import com.android.scidtest.ui.list.viewmodel.ListViewModelFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class DetailsModule {
    @Provides
    fun provideDetailsViewModelFactory(
        booksRepository: BooksRepository,
        @Named("book_id") bookId: Int
    ): DetailsViewModelFactory =
        DetailsViewModelFactory(booksRepository, bookId)
}