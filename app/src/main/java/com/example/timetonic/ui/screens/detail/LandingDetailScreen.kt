package com.example.timetonic.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.example.timetonic.ui.components.ErrorComponent
import com.example.timetonic.ui.components.TopAppBarTimeTonic
import com.example.timetonic.ui.models.BookResume

@Composable
fun LandingDetailScreen(
    navigateToLogin: () -> Unit,
    navigateUp: () -> Unit,
    landingDetailViewModel: LandingDetailViewModel = viewModel(factory = LandingDetailViewModel.provideFactory())
) {
    val landingDetailUiState by landingDetailViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBarTimeTonic(
                canNavigate = true,
                onClickLogOut = { navigateToLogin() },
                onClickUp = navigateUp
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                landingDetailUiState.isLoading -> {
                    CircularProgressIndicator()
                }

                landingDetailUiState.error.isNotEmpty() -> {
                    ErrorComponent(
                        message = landingDetailUiState.error
                    )
                }

                else -> {
                    BookDetail(book = landingDetailUiState.book)
                }
            }
        }
    }
}

@Composable
fun BookDetail(
    book: BookResume,
) {
    Scaffold(
        topBar = {
            Text(
                text = book.title ?: "There is not title for this book",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = Modifier.padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val imageUrl = "$BASE_URL_FOR_IMAGES${book.image}"
            item {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    error = painterResource(R.drawable.book),
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(width = 300.dp, height = 300.dp)
                        .padding(20.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = book.description ?: "There is not description for this book!",
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = book.owner ?: "There is not owner for this book",
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }
    }
}

