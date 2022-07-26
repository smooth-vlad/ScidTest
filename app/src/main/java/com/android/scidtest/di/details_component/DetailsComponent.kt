package com.android.scidtest.di.details_component

import android.content.Context
import com.android.scidtest.data.repository.BooksRepository
import com.android.scidtest.ui.details.viewmodel.DetailsViewModelFactory
import com.android.scidtest.ui.list.viewmodel.ListViewModelFactory
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import javax.inject.Named
import javax.inject.Singleton

/**
 * Dagger component that depends on some book, associated with 'bookId'
 * @param bookId id to link this component with a book
 */
@Subcomponent(modules = [DetailsModule::class])
interface DetailsComponent {
    fun getDetailsViewModelFactory(): DetailsViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(@Named("book_id") @BindsInstance bookId: Int): DetailsComponent
    }
}