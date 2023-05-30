package com.example.polyratings.data


data class ReportRating (
    var professorId: String = "",
    var ratingId: String = "",
    var email: String = "",
    var reason: String = "",
){
    override fun toString(): String {
        return "ReportRating(professorId='$professorId', " +
                "ratingId=$ratingId, email='$email', " +
                "reason=$reason"
    }
}