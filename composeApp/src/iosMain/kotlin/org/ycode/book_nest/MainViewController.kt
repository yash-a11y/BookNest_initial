package org.ycode.book_nest

import androidx.compose.ui.window.ComposeUIViewController
import org.ycode.book_nest.app.App
import org.ycode.book_nest.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }