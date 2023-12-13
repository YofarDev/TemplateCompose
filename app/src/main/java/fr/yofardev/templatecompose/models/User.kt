package fr.yofardev.templatecompose.models

data class User(
    val id: String,
    val email: String,
) {
    // No-argument constructor
    constructor() : this("", "")
}