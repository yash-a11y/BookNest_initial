package com.plcoding.bookpedia.book.presentation.book_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class bookListVM(
): ViewModel()
{

    private val _state  = MutableStateFlow(bookListState())
    val state = _state


    fun onAction(action: BookListAction)
    {
        when(action)
        {
            is BookListAction.bookClick -> {

            }

            is BookListAction.tabSelected -> {
                _state.update {
                    it.copy(selectedTabIndx = action.index)
                }
            }
            is BookListAction.searchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            else -> {

            }
        }
    }
}