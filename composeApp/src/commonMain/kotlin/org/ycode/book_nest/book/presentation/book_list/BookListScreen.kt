package org.ycode.book_nest.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.bookpedia.book.presentation.book_list.components.SearchBar
import org.koin.compose.viewmodel.koinViewModel
import org.ycode.book_nest.app.search_bar
import org.ycode.book_nest.book.domain.Book
import org.ycode.book_nest.core.presentation.grey

@Composable
fun BookListScreen_root(
    viewmodel : bookListVM = koinViewModel(),
)
{
    val state = viewmodel.state.collectAsStateWithLifecycle()

    BookListScrn(
        state = state.value,
        onAction = {
          viewmodel.onAction(it)
        }
    )


}

@Composable
fun BookListScrn(
    state : bookListState,
    onAction: (BookListAction) -> Unit
){
      val keyboardControl = LocalSoftwareKeyboardController.current

     Column(
         modifier = Modifier
             .fillMaxSize()
             .background(grey)
             .statusBarsPadding(),
         horizontalAlignment = Alignment.CenterHorizontally
     ){
         SearchBar(
             searchQuery = state.searchQuery,
             onSearchQueryChange = {
                 onAction(BookListAction.searchQueryChange(it))
             },
             onImeSearch = {
                 keyboardControl?.hide()
             },
             modifier = Modifier
                 .widthIn(max = 400.dp)
                 .fillMaxWidth()
                 .padding(16.dp)

         )
     }
}