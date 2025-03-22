package org.ycode.book_nest.app


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import booknest.composeapp.generated.resources.Res
import booknest.composeapp.generated.resources.compose_multiplatform
import com.plcoding.bookpedia.book.presentation.book_list.components.SearchBar
import org.koin.compose.viewmodel.koinViewModel
import org.ycode.book_nest.Greeting
import org.ycode.book_nest.book.presentation.book_list.BookListScreen_root
import org.ycode.book_nest.book.presentation.book_list.bookListVM

@Composable
@Preview
fun App() {


        val viewmodel = koinViewModel<bookListVM>()

        Box()
        {
            BookListScreen_root()
        }


}

