package com.android.scidtest.di.main_component

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
class MainModule {
    @Singleton
    @Provides
    fun provideCandidateScidService(@Named("base_url") baseUrl: String): CandidateScidService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(CandidateScidService::class.java)
    }

    @Singleton
    @Provides
    fun provideBooksRepository(booksDatabase: BooksDatabase, candidateScidService: CandidateScidService): BooksRepository {
        return BooksRepository(booksDatabase, candidateScidService)
    }

    /**
     * For simplicity base url is provided here
     */
    @Named("base_url")
    @Provides
    fun provideBaseUrl(): String {
        return "http://candidate.scid.ru/api/"
    }

    @Provides
    fun provideListViewModelFactory(booksRepository: BooksRepository): ListViewModelFactory =
        ListViewModelFactory(booksRepository)

    @Singleton
    @Provides
    fun provideBooksDatabase(context: Context): BooksDatabase {
        return Room.databaseBuilder(context, BooksDatabase::class.java, "books_db").build()
    }
}