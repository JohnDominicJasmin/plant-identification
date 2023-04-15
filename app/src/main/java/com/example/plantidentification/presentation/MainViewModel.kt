package com.example.plantidentification.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantidentification.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MainViewModel
    @Inject constructor(
        private val mainRepository: MainRepository
    ) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    fun recognizeFood(imagePath:String){
        viewModelScope.launch(context = Dispatchers.IO) {
            runCatching {
                mainRepository.recognizeFood(imagePath)
            }.onSuccess {
                Timber.v("Result is $it")
            }.onFailure {
                Timber.e("Error is ${it.message}")
            }
        }
    }



}