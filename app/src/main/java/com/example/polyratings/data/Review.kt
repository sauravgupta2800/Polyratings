package com.example.polyratings.data

class Review(
    val courseType: String,
    val grade: String,
    val gradeLevel: String,
    val id: String,
    val overallRating: Double,
    val postDate: String,
    val presentsMaterialClearly: Double,
    val professor: String,
    val rating: String,
    val recognizesStudentDifficulties: Double
) {

    override fun toString(): String {
        return "Rating(courseType='$courseType', grade='$grade', gradeLevel='$gradeLevel', " +
                "id='$id', overallRating=$overallRating, postDate='$postDate', " +
                "presentsMaterialClearly=$presentsMaterialClearly, professor='$professor', " +
                "rating='$rating', recognizesStudentDifficulties=$recognizesStudentDifficulties)"
    }
}