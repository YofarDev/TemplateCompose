package fr.yofardev.templatecompose.models

data class User(
    val id: String,
    val email: String,
    val username: String = "",
    val profilePicture: String = "",
    val bio: String = "",
    val followers: List<String> = listOf(),
    val following: List<String> = listOf(),
) {
    // No-argument constructor
    constructor() : this("", "")
}