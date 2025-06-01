package org.ycode.book_nest.book.presentation.book_detail

import org.ycode.book_nest.book.domain.Book

sealed interface BookDetailAction {
    data object OnBackClick: BookDetailAction
    data object OnFavoriteClick: BookDetailAction
    data class OnSelectedBookChange(val book: Book): BookDetailAction
}