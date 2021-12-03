package com.example.demo.user.dtos

import com.example.demo.interfaces.UserDataValidator

class RegisterUserDto(
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var isAdmin: Boolean = false
): UserDataValidator {

    /**
     * @return True if the email, password and name are not empty
     */
    fun validate(): Boolean {
        return this.notEmpty(this.email) && this.notEmpty(this.password) && this.notEmpty(this.name)
    }
}