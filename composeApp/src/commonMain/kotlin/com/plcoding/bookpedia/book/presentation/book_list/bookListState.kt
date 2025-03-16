package com.plcoding.bookpedia.book.presentation.book_list

import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.presentation.UiText

data class bookListState(
    val searchQuery: String = "kotlin",
    val searchResult: List<Book> = emptyList(),
    val favBooks: List<Book> = emptyList(),
    val isLoad: Boolean = true,
    val selectedTabIndx: Int = 0,
    val errMessage: UiText? = null
)
