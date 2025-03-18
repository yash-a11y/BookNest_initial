package org.ycode.book_nest

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.ycode.book_nest.app.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "BookNest",
    ) {
        App()
    }
}