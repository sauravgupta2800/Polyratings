package com.example.polyratings.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.polyratings.APIService
import com.example.polyratings.ApiResponse
import com.example.polyratings.ApiResponseDetails
import com.google.gson.internal.LinkedTreeMap
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PolyratingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun increaseCount() {
        _uiState.update { currentState ->
            val updatedCount = currentState.count + 1
            println("increaseCount $updatedCount")

            currentState.copy(
                count = updatedCount,
            )
        }
    }

    fun handleSearchKeyChange(value: String) {
        println("searchKey: ${value}")
        _uiState.update { currentState ->
            currentState.copy(
                searchKey = value,
            )
        }
    }

    fun updateCurrentProfessor(professor: Professor){
        _uiState.update { currentState ->
            currentState.copy(
                currentProfessor = professor
            )
        }
    }

    fun getProfessorDetails(professorId: String) {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            _uiState.update { currentState ->
                currentState.copy(
                    fetchingCurrentProfessor = true,
                )
            }
            try {
                println("Before API Details: $professorId")
//                %7B"id"%3A"2ce8c1b8-2a6b-4990-887c-96091141be93"%7D
                val response = apiService.getProfessorDetails("%7B\"id\"%3A\"${professorId}\"%7D")
                if (response.isSuccessful) {
                    val apiResponse: ApiResponseDetails? = response.body()
                    val professor: Professor = (apiResponse?.result?.data ?: null) as Professor;

                    println("After API Details:")
                    println(professor)
                    _uiState.update { currentState ->
                        currentState.copy(
                            currentProfessor = professor,
                            fetchingCurrentProfessor = false,
                        )
                    }
                }else{
                    println(response)
                }

            } catch (e: Exception) {
                println("ERROR")
                println(e.message.toString())
            } finally {
                _uiState.update { currentState ->
                    currentState.copy(
                        fetchingCurrentProfessor = false,
                    )
                }
            }
        }
    }

    fun getProfessorList() {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                println("Before API List: ")
                val response = apiService.getProfessors()
                if (response.isSuccessful) {
                    val apiResponse: ApiResponse? = response.body()
                    val professors: List<Professor> = apiResponse?.result?.data ?: emptyList()

                    _uiState.update { currentState ->
                        println("Professors: $professors")
                        currentState.copy(
                            fetchingProfessors = false,
                            professorList = professors,
                        )
                    }
                }

            } catch (e: Exception) {
                println("ERROR")
                println(e.message.toString())
//                errorMessage = e.message.toString()
            } finally {

            }
        }
    }
}
