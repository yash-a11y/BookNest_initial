package org.ycode.book_nest.book.data.repository

import androidx.sqlite.SQLiteException
import org.ycode.book_nest.book.data.database.FavoriteBookDao
import org.ycode.book_nest.book.data.mappers.toBook
import org.ycode.book_nest.book.data.mappers.toBookEntity
import org.ycode.book_nest.book.data.network.RemoteBookDataSource
import org.ycode.book_nest.book.domain.Book
import org.ycode.book_nest.book.domain.BookRepository
import org.ycode.book_nest.core.domain.DataError
import org.ycode.book_nest.core.domain.EmptyResult
import org.ycode.book_nest.core.domain.Result
import org.ycode.book_nest.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao
): BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError> {
        val localResult = favoriteBookDao.getFavoriteBook(bookId)

        return if(localResult == null) {
            remoteBookDataSource
                .getBookDetails(bookId)
                .map { it.description }
        } else {
            Result.Success(localResult.description)
        }
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.map { it.toBook() }
            }
    }

    override fun isBookFavorite(id: String): Flow<Boolean> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.any { it.id == id }
            }
    }

    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        return try {
            favoriteBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch(e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        } catch(e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun deleteFromFavorites(id: String) {
        try {
            favoriteBookDao.deleteFavoriteBook(id)
        } catch(e: Exception) {
            // Log the error but don't crash
            println("Error deleting favorite book: ${e.message}")
        }
    }
}