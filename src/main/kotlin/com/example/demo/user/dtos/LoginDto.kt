package com.example.demo.user.dtos

class LoginDto(
    val email: String? = null,
    val password: String? = null
) {

    /**
     * @param data String
     * @return True if the data is not empty, and false if it's null or empty
     */
    private fun notEmpty(data: String?): Boolean {
        if (data != null) {
            return data.trim().isNotEmpty()
        }
        return false
    }

    /**
     * @return True if the email and the password are not empty
     */
    fun validate(): Boolean {
        return this.notEmpty(this.email) && this.notEmpty(this.password)
    }
}