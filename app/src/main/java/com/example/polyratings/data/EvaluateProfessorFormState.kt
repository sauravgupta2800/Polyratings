package com.example.polyratings.data

import com.example.polyratings.data.Constants.COURSE_TYPES
import com.example.polyratings.data.Constants.GRADES
import com.example.polyratings.data.Constants.GRADE_LEVELS

data class EvaluateProfessorFormState (
        var professor: String = "",
        var courseNum: Int = 0,
        var department: String = "",
        var overallRating: Int = 4,
        var presentsMaterialClearly: Int = 4,
        var recognizesStudentDifficulties: Int = 4,
        var grade: String = GRADES[0],
        var courseType: String = COURSE_TYPES[0],
        var rating: String = "",
        var gradeLevel: String = GRADE_LEVELS[0],
//        var tags: List<String> = listOf()
        ){
        override fun toString(): String {
                return "EvaluateProfessorFormState(professorId='$professor', " +
                        "courseNum=$courseNum, department='$department', " +
                        "overallRating=$overallRating, presentsMaterialClearly=$presentsMaterialClearly, " +
                        "recognizesStudentDifficulties=$recognizesStudentDifficulties, " +
                        "grade='$grade', courseType='$courseType', rating='$rating', " +
                        "gradeLevel='$gradeLevel'"
        }
}