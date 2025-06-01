package org.ycode.book_nest

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.ycode.book_nest.app.App
import org.ycode.book_nest.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "CMP-Bookpedia",
        ) {
            App()
        }
    }
}