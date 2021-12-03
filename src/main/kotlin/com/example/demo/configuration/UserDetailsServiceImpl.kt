package com.example.demo.configuration

import com.example.demo.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    @Autowired
    val db: UserRepository
): UserDetailsService {



    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    override fun loadUserByUsername(login: String): UserDetails {
        val user =  db.getByEmail(login)
            .orElseThrow { UsernameNotFoundException("User not found!") }

        val roles = if (user.isAdmin) arrayOf("ADMIN", "USER") else arrayOf("USER")

        return User
                .builder()
                .username(user.email)
                .password(user.password)
                .roles(*roles)
                .build()
    }
}