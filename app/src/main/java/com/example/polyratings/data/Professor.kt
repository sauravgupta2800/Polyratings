package com.example.polyratings.data

class Professor(
    val courses: List<String>,
    val department: String,
    val firstName: String,
    val id: String,
    val lastName: String,
    val materialClear: Double,
    val numEvals: Int,
    val overallRating: Double,
    val studentDifficulties: Double,
    val reviews: MutableMap<String, List<Review>>? = mutableMapOf()
) {
    override fun toString(): String {
        return "Professor (courses=$courses, department='$department', firstName='$firstName', " +
                "id='$id', lastName='$lastName', materialClear=$materialClear, numEvals=$numEvals, " +
                "overallRating=$overallRating, studentDifficulties=$studentDifficulties), reviews=${reviews?.keys})"
    }
}