package org.ycode.book_nest.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import booknest.composeapp.generated.resources.Res
import booknest.composeapp.generated.resources.favorites
import booknest.composeapp.generated.resources.no_results
import booknest.composeapp.generated.resources.search_results
import com.plcoding.bookpedia.book.presentation.book_list.components.SearchBar
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.ycode.book_nest.app.search_bar
import org.ycode.book_nest.book.domain.Book
import org.ycode.book_nest.book.presentation.book_list.components.BookList
import org.ycode.book_nest.core.presentation.DarkBlue
import org.ycode.book_nest.core.presentation.DesertWhite
import org.ycode.book_nest.core.presentation.SandYellow
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
      val pagerState = rememberPagerState{ 2 }
      val searchResultState = rememberLazyListState()
      val favBookScrollState = rememberLazyListState()
    LaunchedEffect(state.searchResult)
    {
        searchResultState.animateScrollToItem(0)
    }

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

         Surface(
             modifier = Modifier.weight(1f)
                 .fillMaxWidth(),
             color = DarkBlue,
             shape = RoundedCornerShape(
                 topStart = 35.dp,
                 topEnd = 35.dp
             )

         )
         {
             Column(
                 horizontalAlignment = Alignment.CenterHorizontally
             ){
                 TabRow(
                     selectedTabIndex = state.selectedTabIndx,
                     modifier = Modifier
                         .padding(vertical = 20.dp, horizontal = 20.dp)
                         .widthIn(max = 700.dp)
                         .fillMaxWidth()
                         .clip(RoundedCornerShape(10.dp)),
                     containerColor = DesertWhite,
                     indicator = { tabPositions ->
                         TabRowDefaults.SecondaryIndicator(
                             color = SandYellow,
                             modifier = Modifier
                                 .tabIndicatorOffset(tabPositions[state.selectedTabIndx])
                         )
                     }
                 ) {
                     Tab(
                         selected = state.selectedTabIndx == 0,
                         onClick = {
                             onAction(BookListAction.tabSelected(0))
                         },
                         modifier = Modifier.weight(1f),
                         selectedContentColor = SandYellow,
                         unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                     ) {
                         Text(
                             text = stringResource(Res.string.search_results),
                             modifier = Modifier
                                 .padding(vertical = 12.dp)
                         )
                     }
                     Tab(
                         selected = state.selectedTabIndx == 1,
                         onClick = {
                             onAction(BookListAction.tabSelected(1))
                         },
                         modifier = Modifier.weight(1f),
                         selectedContentColor = SandYellow,
                         unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                     ) {
                         Text(
                             text = stringResource(Res.string.favorites),
                             modifier = Modifier
                                 .padding(vertical = 12.dp)
                         )
                     }
                 }
                 Spacer(modifier = Modifier.height(4.dp))

                 HorizontalPager(
                 state = pagerState,
                 modifier = Modifier.fillMaxWidth().weight(1f)
                 ){
                     pageindex ->
                     Box(
                         modifier = Modifier.fillMaxSize(),
                         contentAlignment = Alignment.Center
                     ) {
                         when (pageindex) {
                             0 -> {
                                 if (state.isLoad) {
                                     CircularProgressIndicator()
                                 }
                                 else{
                                     when{
                                         state.errMessage != null -> {
                                             Text(
                                                 text = state.errMessage.asString(),
                                                 textAlign = TextAlign.Center,
                                                 style = MaterialTheme.typography.headlineSmall,
                                                 color = MaterialTheme.colorScheme.error
                                             )
                                         }
                                         state.searchResult.isEmpty() -> {
                                             Text(
                                                 text = stringResource(Res.string.no_results),
                                                 textAlign = TextAlign.Center,
                                                 style = MaterialTheme.typography.headlineSmall
                                             )
                                         }
                                         else -> {

                                            BookList(
                                                books = state.searchResult,
                                                onBookClick = {
                                                    onAction(BookListAction.bookClick(it))
                                                },
                                                modifier = Modifier
                                                    .widthIn(max = 700.dp)
                                                ,
                                                scrollState =  searchResultState
                                            )


                                         }                                         }
                                 }
                             }

                             1 -> {
                                 if(state.favBooks.isEmpty())
                                 {
                                     Text(
                                         text = stringResource(Res.string.no_results),
                                         textAlign = TextAlign.Center,
                                         style = MaterialTheme.typography.headlineSmall
                                     )
                                 }
                                 else{
                                     BookList(
                                         books = state.favBooks,
                                         onBookClick = {
                                             onAction(BookListAction.bookClick(it))
                                         },
                                         modifier = Modifier
                                             .fillMaxWidth()
                                             ,
                                         scrollState = favBookScrollState
                                     )
                                 }
                             }
                         }
                     }
                 }

             }

         }

     }
}