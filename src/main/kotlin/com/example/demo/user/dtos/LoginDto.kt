package com.example.demo.user.dtos

import com.example.demo.interfaces.UserDataValidator

class LoginDto(
    val email: String? = null,
    val password: String? = null
): UserDataValidator {
    /**
     * @return True if the email and the password are not empty
     */
    fun validate(): Boolean {
        return this.notEmpty(this.email) && this.notEmpty(this.password)
    }
}