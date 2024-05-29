package com.example.timetonic.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.timetonic.R
import com.example.timetonic.data.BASE_URL_FOR_IMAGES
import com.example.timetonic.ui.models.BookResume

@Composable
fun LandingDetailScreen(
    landingDetailViewModel: LandingDetailViewModel = viewModel(factory = LandingDetailViewModel.provideFactory())
) {
    val landingDetailUiState by landingDetailViewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            landingDetailUiState.isLoading -> {
                CircularProgressIndicator()
            }

            landingDetailUiState.error.isNotEmpty() -> {
                BookDetailError(landingDetailUiState.error)
            }

            else -> {
                BookDetail(book = landingDetailUiState.book)
            }
        }
    }
}

@Composable
fun BookDetail(
    book: BookResume,
) {
    Scaffold(
        topBar = { Text(text = book.title ?: "There is not title for this book") },
    ) {
        Column(modifier = Modifier.padding(it)) {
            val imageUrl = "$BASE_URL_FOR_IMAGES${book.image}"
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                error = painterResource(R.drawable.book)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = book.description ?: "There is not description for this book!")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = book.owner ?: "There is not owner for this book")
        }
    }
}

@Composable
fun BookDetailError(
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

