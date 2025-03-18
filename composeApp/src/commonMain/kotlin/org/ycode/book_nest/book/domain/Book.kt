package org.ycode.book_nest.book.domain

data class Book(
    val id: String,
    val title: String,
    val imageUrl: String,
    val authorsval : List<String>,
    val description: String?,
    val language: List<String>,
    val firstPublishYear:String ?,
    val avgRating : Double?,
    val ratingCount: Int?,
    val numPages: Int?,
    val numEditions: Int

)