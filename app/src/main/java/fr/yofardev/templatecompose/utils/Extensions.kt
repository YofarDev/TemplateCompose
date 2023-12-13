package fr.yofardev.templatecompose.utils



fun String.isValidEmail(): Boolean {
    // Define a regular expression pattern for a simple email validation
    val emailPattern = Regex("^\\S+@\\S+\\.\\S+$")
    return emailPattern.matches(this)
}

    // Password must be at least 6 characters long
    fun String.isValidPassword(): Boolean {
        return this.length >= 6
    }

