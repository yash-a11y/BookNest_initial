package org.ycode.book_nest.book.presentation.book_list


import org.ycode.book_nest.book.domain.Book
import org.ycode.book_nest.core.presentation.UiText

data class bookListState(
    val searchQuery: String = "kotlin",
    val searchResult: List<Book> = emptyList(),
    val favBooks: List<Book> = emptyList(),
    val isLoad: Boolean = true,
    val selectedTabIndx: Int = 0,
    val errMessage: UiText? = null
)
