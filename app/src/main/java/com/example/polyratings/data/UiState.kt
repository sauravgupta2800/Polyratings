package com.example.polyratings.data

data class UiState(
    val count:Int = 0,
    var searchKey:String = "",
    var fetchingProfessors: Boolean = true,
    val professorList: List<Professor> = listOf(),
    val data: Any? = null,
    val todoList:  List<Any> = listOf(),
    val fetchingCurrentProfessor: Boolean = true,
    val currentProfessor: Professor? = null,
)