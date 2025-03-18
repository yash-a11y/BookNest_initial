package org.ycode.book_nest

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform