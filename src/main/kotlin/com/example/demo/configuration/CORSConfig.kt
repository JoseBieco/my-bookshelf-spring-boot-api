package com.example.demo.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class CORSConfig: WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
       registry.addMapping("/**")
           .allowedOrigins("http://localhost:8081", "http://localhost:8080")
           .allowCredentials(true)
    }
}