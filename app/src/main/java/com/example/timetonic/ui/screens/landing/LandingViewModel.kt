package com.example.timetonic.ui.screens.landing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.timetonic.data.RetrofitServiceFactory
import com.example.timetonic.data.model.books.AllBooks
import com.example.timetonic.data.repository.books.BooksRepositoryImpl
import com.example.timetonic.domain.landing.BooksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LandingUiState(
    val isLoading: Boolean = false,
    val allBooks: AllBooks = AllBooks(
        books = emptyList(),
        contacts = emptyList(),
        nbBooks = 0,
        nbContacts = 0
    ),
    val error: String = ""
)

class LandingViewModel(
    savedStateHandle: SavedStateHandle,
    private val bookRepository: BooksRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<LandingUiState> = MutableStateFlow(LandingUiState())
    val uiState: StateFlow<LandingUiState> = _uiState.asStateFlow()

    init {
        val sesskey: String = checkNotNull(savedStateHandle["sesskey"])
        val o_u: String = checkNotNull(savedStateHandle["o_u"])
        getAllBooks(sesskey, o_u)
    }

    private fun getAllBooks(sesskey: String, o_u: String) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                bookRepository.getAllBooks(sesskey = sesskey, o_u = o_u).collect { allBooks ->
                    _uiState.update {
                        it.copy(isLoading = false, allBooks = allBooks)
                    }
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

                return LandingViewModel(
                    savedStateHandle,
                    BooksRepositoryImpl(RetrofitServiceFactory.makeRetrofitService())
                ) as T
            }
        }
    }
}