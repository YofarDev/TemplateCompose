package fr.yofardev.templatecompose.utils



    fun String.isValidEmail(): Boolean {
        val emailRegex = "^A-Za-z([@]{1})(.{1,})(\\.)(.{1,})".toRegex()
        return this.matches(emailRegex)
    }

    // Password must be at least 6 characters long
    fun String.isValidPassword(): Boolean {
        return this.length >= 6
    }

