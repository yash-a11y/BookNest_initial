package com.plcoding.bookpedia.book.presentation.book_list

import com.plcoding.bookpedia.book.domain.Book

sealed interface BookListAction {

    data class searchQueryChange(val query: String) : BookListAction
    data class bookClick(val book: Book) : BookListAction
    data class tabSelected(val index : Int) : BookListAction
}