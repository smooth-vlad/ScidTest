package com.android.scidtest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Book::class], version = 1)
abstract class BooksDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao
}