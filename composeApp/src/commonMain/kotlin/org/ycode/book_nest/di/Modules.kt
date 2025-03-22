package org.ycode.book_nest.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.ycode.book_nest.book.presentation.book_list.bookListVM



val sharedModule = module {


    viewModelOf(::bookListVM)

}