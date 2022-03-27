package com.example.demo.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * CORS config
 */
@Configuration
@EnableWebMvc
class CORSConfig: WebMvcConfigurer {

    /**
     * Configure the CORS settings.
     * Update allowedOrigins latter
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("https://mybookshelf.com")
            .allowCredentials(true)
    }
}