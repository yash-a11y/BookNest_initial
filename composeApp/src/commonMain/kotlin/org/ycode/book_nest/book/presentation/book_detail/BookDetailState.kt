package org.ycode.book_nest.book.presentation.book_detail


import org.ycode.book_nest.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val book: Book? = null
)
