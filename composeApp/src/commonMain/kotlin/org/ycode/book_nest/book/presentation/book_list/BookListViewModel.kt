package org.ycode.book_nest.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import org.ycode.book_nest.book.domain.Book
import org.ycode.book_nest.book.domain.BookRepository
import org.ycode.book_nest.core.domain.onError
import org.ycode.book_nest.core.domain.onSuccess
import org.ycode.book_nest.core.presentation.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ycode.book_nest.core.domain.DataError

class BookListViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private var cachedBooks = emptyList<Book>()
    private var searchJob: Job? = null
    private var observeFavoriteJob: Job? = null

    private val _state = MutableStateFlow(bookListState())
    val state = _state
        .onStart {
            if(cachedBooks.isEmpty()) {
                observeSearchQuery()
            }
            observeFavoriteBooks()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.bookClick -> {

            }

            is BookListAction.searchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }

            is BookListAction.tabSelected -> {
                _state.update {
                    it.copy(selectedTabIndx = action.index)
                }
            }
        }
    }

    private fun observeFavoriteBooks() {
        observeFavoriteJob?.cancel()
        observeFavoriteJob = bookRepository
            .getFavoriteBooks()
            .onEach { favoriteBooks ->
                _state.update { it.copy(
                    favBooks = favoriteBooks
                ) }
            }
            .launchIn(viewModelScope)
    }

    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errMessage = null,
                                searchResult = cachedBooks
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        try {
            _state.update {
                it.copy(
                    isLoad = true,
                    errMessage = null
                )
            }
            bookRepository
                .searchBooks(query)
                .onSuccess { searchResults ->
                    _state.update {
                        it.copy(
                            isLoad = false,
                            errMessage = null,
                            searchResult = searchResults
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            searchResult = emptyList(),
                            isLoad = false,
                            errMessage = error.toUiText()
                        )
                    }
                }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    searchResult = emptyList(),
                    isLoad = false,
                    errMessage = DataError.Remote.UNKNOWN.toUiText()
                )
            }
        }
    }

}