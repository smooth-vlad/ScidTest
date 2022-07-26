package com.android.scidtest.di.main_component

import android.content.Context
import com.android.scidtest.data.repository.BooksRepository
import com.android.scidtest.di.details_component.DetailsComponent
import com.android.scidtest.ui.details.viewmodel.DetailsViewModelFactory
import com.android.scidtest.ui.list.viewmodel.ListViewModelFactory
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent.Factory
import javax.inject.Singleton

/**
 * Main Dagger Component
 * @param applicationContext context - used to create local database
 */
@Singleton
@Component(modules = [MainModule::class])
interface MainComponent {
    fun getBooksRepository(): BooksRepository
    fun getListViewModelFactory(): ListViewModelFactory
    fun getDetailsComponentFactory(): DetailsComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): MainComponent
    }
}