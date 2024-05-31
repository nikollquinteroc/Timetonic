package com.example.timetonic.ui.screens.landing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.timetonic.R
import com.example.timetonic.data.BASE_URL_FOR_IMAGES
import com.example.timetonic.data.model.books.Book
import com.example.timetonic.data.model.books.BookResponse

@Composable
fun LandingScreen(
    navigateToBookDetail: (BookResponse) -> Unit,
    navigateToLogin: () -> Unit,
    sesskey: String,
    o_u: String,
    landingViewModel: LandingViewModel = viewModel(factory = LandingViewModel.provideFactory())
) {
    val landingUiState by landingViewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            landingUiState.isLoading -> {
                CircularProgressIndicator()
            }

            landingUiState.error.isNotEmpty() -> {
                LandingError(landingUiState.error)
            }

            else -> {
                /*LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 128.dp)
                ) {
                    items(landingUiState.allBooks.books) { book ->
                        BookCard(book)
                    }
                }*/

                Button(onClick = { navigateToLogin() }) {
                    Text(text = "Logout")
                }

                LazyColumn {
                    items(landingUiState.allBooks.books) { book ->
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
            .size(width = 240.dp, height = 240.dp),
        onClick = onClickBook
    ) {
        val imageUrl = "$BASE_URL_FOR_IMAGES${book.ownerPrefs.oCoverImg}"
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            error = painterResource(R.drawable.book)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = book.ownerPrefs.title ?: "There is not title for this book",
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun LandingError(
    message: String,
) {
    Scaffold(
        topBar = { Text(text = "Error") },
    ) {
        Column(modifier = Modifier.padding(it)) {
            // Imagen con icono de error poner acá
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = message)
        }
    }
}
