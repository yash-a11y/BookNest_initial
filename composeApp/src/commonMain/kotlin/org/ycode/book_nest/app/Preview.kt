package org.ycode.book_nest.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ycode.book_nest.book.presentation.book_list.components.SearchBar
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun search_bar()
{
    Box(modifier = Modifier.fillMaxSize(1f).padding(10.dp)){
        SearchBar(
            searchQuery = "kotlin",
            modifier = Modifier.fillMaxWidth(1f),
            onSearchQueryChange = {

            },
            onImeSearch = {

            }

        )
    }

}