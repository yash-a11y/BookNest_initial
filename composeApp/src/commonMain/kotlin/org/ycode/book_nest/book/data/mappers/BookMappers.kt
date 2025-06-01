package org.ycode.book_nest.book.data.mappers

import org.ycode.book_nest.book.data.database.BookEntity
import org.ycode.book_nest.book.data.dto.SearchedBookDto
import org.ycode.book_nest.book.domain.Book

fun SearchedBookDto.toBook(): Book {
    return Book(
        id = id.substringAfterLast("/"),
        title = title,
        imageUrl = if(coverKey != null) {
            "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
        } else {
            "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg"
        },
        authorsval = authorNames ?: emptyList(),
        description = null,
        language = languages ?: emptyList(),
        firstPublishYear = firstPublishYear.toString(),
        avgRating = ratingsAverage,
        ratingCount = ratingsCount,
        numPages = numPagesMedian,
        numEditions = numEditions ?: 0
    )
}

fun Book.toBookEntity(): BookEntity {
    return BookEntity(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = language,
        authors = authorsval,
        firstPublishYear = firstPublishYear,
        ratingsAverage = avgRating,
        ratingsCount = ratingCount,
        numPagesMedian = numPages,
        numEditions = numEditions
    )
}

fun BookEntity.toBook(): Book {
    return Book(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        language = languages,
        authorsval = authors,
        firstPublishYear = firstPublishYear,
        avgRating = ratingsAverage,
        ratingCount = ratingsCount,
        numPages = numPagesMedian,
        numEditions = numEditions
    )
}