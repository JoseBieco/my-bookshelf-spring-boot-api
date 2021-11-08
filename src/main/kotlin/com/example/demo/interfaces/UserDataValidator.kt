package com.example.demo.interfaces

/**
 * Interface that will receive all user data validators
 */
interface UserDataValidator {

    /**
     * @param data String
     * @return True if the data is not empty, and false if it's null or empty
     */
    fun notEmpty(data: String?): Boolean {
        if (data != null) {
            return data.trim().isNotEmpty()
        }
        return false
    }
}