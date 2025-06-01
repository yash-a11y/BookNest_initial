package org.ycode.book_nest.book.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ycode.book_nest.book.domain.Book

class SelectedBookViewModel: ViewModel() {

    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook = _selectedBook.asStateFlow()

    fun onSelectBook(book: Book?) {
        _selectedBook.value = book
    }
}