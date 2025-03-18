package org.ycode.book_nest.book.presentation.book_list

import org.ycode.book_nest.book.domain.Book


sealed interface BookListAction {

    data class searchQueryChange(val query: String) : BookListAction
    data class bookClick(val book: Book) : BookListAction
    data class tabSelected(val index : Int) : BookListAction
}