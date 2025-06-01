package org.ycode.book_nest.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.ycode.book_nest.book.data.database.DatabaseFactory
import org.ycode.book_nest.book.data.database.FavoriteBookDatabase
import org.ycode.book_nest.book.data.network.KtorRemoteBookDataSource
import org.ycode.book_nest.book.data.network.RemoteBookDataSource
import org.ycode.book_nest.book.data.repository.DefaultBookRepository
import org.ycode.book_nest.book.domain.BookRepository
import org.ycode.book_nest.book.presentation.SelectedBookViewModel
import org.ycode.book_nest.book.presentation.book_detail.BookDetailViewModel
import org.ycode.book_nest.book.presentation.book_list.BookListViewModel
import org.ycode.book_nest.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<FavoriteBookDatabase>().favoriteBookDao }

    viewModelOf(::BookListViewModel)
    viewModelOf(::BookDetailViewModel)
    viewModelOf(::SelectedBookViewModel)
}