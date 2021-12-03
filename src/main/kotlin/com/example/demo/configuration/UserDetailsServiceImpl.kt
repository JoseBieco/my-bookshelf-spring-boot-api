package com.example.demo.configuration

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailServiceImpl: UserDetailsService {

    override fun loadUserByUsername(login: String): UserDetails {
        TODO("Not yet implemented")
    }
}