package com.example.demo.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class AppConfig: WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
       registry.addMapping("/**")
           .allowedOrigins("http://localhost:8081", "http://localhost:8080")
           .allowCredentials(true)
    }

    @Bean
    fun encoder(): PasswordEncoder? {
        return BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.`$2A`)
    }
}