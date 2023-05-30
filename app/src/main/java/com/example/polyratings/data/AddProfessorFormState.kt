package com.example.polyratings.data


import com.example.polyratings.data.Constants.COURSE_TYPES
import com.example.polyratings.data.Constants.DEPARTMENT_LIST
import com.example.polyratings.data.Constants.GRADES
import com.example.polyratings.data.Constants.GRADE_LEVELS

data class AddProfessorFormState (
    var firstName: String = "",
    var lastName: String = "",
    var department: String = DEPARTMENT_LIST[0],
    var rating: EvaluateProfessorFormState = EvaluateProfessorFormState(),
){
    override fun toString(): String {
        return "AddProfessorFormState(firstName='$firstName', " +
                "lastName=$lastName, department='$department', " +
                "rating=$rating"
    }
}