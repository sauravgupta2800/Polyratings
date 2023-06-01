package com.example.polyratings.data


class FaqItem(
    val question: String,
    val answer: String
    ) {
    override fun toString(): String {
        return "FaqItem (question=$question, answer=$answer)"
    }
}