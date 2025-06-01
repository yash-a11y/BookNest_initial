package org.ycode.book_nest.book.data.network

import org.ycode.book_nest.book.data.dto.BookWorkDto
import org.ycode.book_nest.book.data.dto.SearchResponseDto
import org.ycode.book_nest.core.domain.DataError
import org.ycode.book_nest.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote>
}