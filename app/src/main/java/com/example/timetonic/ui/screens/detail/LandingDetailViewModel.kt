package com.example.timetonic.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.timetonic.data.RetrofitServiceFactory
import com.example.timetonic.data.repository.books.BooksRepositoryImpl
import com.example.timetonic.domain.landing.BooksRepository
import com.example.timetonic.ui.models.BookResume
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LandingDetailUiState(
    val isLoading: Boolean = false,
    val book: BookResume = BookResume(title = "", image = "", owner = "", description = ""),
    val error: String = ""
)

class LandingDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val bookDetailRepository: BooksRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<LandingDetailUiState> =
        MutableStateFlow(LandingDetailUiState())
    val uiState: StateFlow<LandingDetailUiState> = _uiState.asStateFlow()

    init {
        val sesskey: String = checkNotNull(savedStateHandle["sesskey"])
        val o_u: String = checkNotNull(savedStateHandle["o_u"])
        val b_c: String = checkNotNull(savedStateHandle["b_c"])
        getBookInfo(sesskey, o_u, b_c)
    }

    private fun getBookInfo(sesskey: String, o_u: String, b_c: String) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                bookDetailRepository.getBookInfo(sesskey = sesskey, o_u = o_u, b_c = b_c)
                    .collect { book ->
                        val bookResume = BookResume(
                            title = book.ownerPrefs.title,
                            image = book.ownerPrefs.oCoverImg,
                            owner = book.b_o,
                            description = book.description
                        )
                        _uiState.update { it.copy(isLoading = false, book = bookResume) }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Network request failed. Try again. \n${e.message}"
                    )
                }
            }
        }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                val savedStateHandle = extras.createSavedStateHandle()

                return LandingDetailViewModel(
                    savedStateHandle,
                    BooksRepositoryImpl(RetrofitServiceFactory.makeRetrofitService())
                ) as T
            }
        }
    }
}
