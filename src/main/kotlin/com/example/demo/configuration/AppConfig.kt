package com.example.demo.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class AppConfig {

    @Bean
    fun encoder(): PasswordEncoder? {
        return BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.`$2A`)
    }
}