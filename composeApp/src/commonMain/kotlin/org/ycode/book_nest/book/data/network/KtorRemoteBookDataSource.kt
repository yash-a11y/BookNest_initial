package org.ycode.book_nest.book.data.network

import org.ycode.book_nest.book.data.dto.BookWorkDto
import org.ycode.book_nest.book.data.dto.SearchResponseDto
import org.ycode.book_nest.book.domain.Book
import org.ycode.book_nest.core.data.safeCall
import org.ycode.book_nest.core.domain.DataError
import org.ycode.book_nest.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
): RemoteBookDataSource {

    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall<SearchResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/search.json"
            ) {
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter("language", "eng")
                parameter("fields", "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count")
            }
        }
    }

    override suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote> {
        return safeCall<BookWorkDto> {
            httpClient.get(
                urlString = "$BASE_URL/works/$bookWorkId.json"
            )
        }
    }
}