package com.example.timetonic.ui.screens.landing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.timetonic.R
import com.example.timetonic.data.BASE_URL_FOR_IMAGES
import com.example.timetonic.data.model.books.Book
import com.example.timetonic.data.model.books.BookResponse
import com.example.timetonic.ui.components.ErrorComponent
import com.example.timetonic.ui.components.TopAppBarTimeTonic

@Composable
fun LandingScreen(
    navigateToBookDetail: (BookResponse) -> Unit,
    navigateToLogin: () -> Unit,
    sesskey: String,
    o_u: String,
    landingViewModel: LandingViewModel = viewModel(factory = LandingViewModel.provideFactory())
) {
    val landingUiState by landingViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBarTimeTonic(
                canNavigate = false,
                onClickLogOut = { navigateToLogin() },
                onClickUp = { }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                landingUiState.isLoading -> {
                    CircularProgressIndicator()
                }

                landingUiState.error.isNotEmpty() -> {
                    ErrorComponent(
                        message = landingUiState.error
                    )
                }

                else -> {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(id = R.string.books),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn {
                        items(landingUiState.allBooks.books) { book ->
                            Spacer(modifier = Modifier.height(20.dp))
                            BookCard(
                                book = book,
                                onClickBook = {
                                    navigateToBookDetail(
                                        BookResponse(
                                            sesskey = sesskey,
                                            o_u = o_u,
                                            b_c = book.b_c
                                        )
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookCard(
    book: Book,
    onClickBook: () -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .size(width = 340.dp, height = 340.dp),
        onClick = onClickBook
    ) {
        val imageUrl = "$BASE_URL_FOR_IMAGES${book.ownerPrefs.oCoverImg}"
        Column {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    error = painterResource(R.drawable.book),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = book.ownerPrefs.title ?: "There is not title for this book",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = book.b_o,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Preview
@Composable
fun Preview() {
    MaterialTheme {
        ErrorComponent(message = "Hello")
    }
}
