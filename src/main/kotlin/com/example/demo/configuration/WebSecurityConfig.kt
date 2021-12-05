package com.example.demo.configuration

import com.example.demo.configuration.jwt.JwtAuthFilter
import com.example.demo.configuration.jwt.JwtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.OncePerRequestFilter

@EnableWebSecurity
class WebSecurityConfig(
): WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var userDetailService: UserDetailsServiceImpl

    @Autowired
    lateinit var jwtService: JwtService

    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.`$2A`)
    }

    @Bean
    fun jwtFilter(): OncePerRequestFilter {
        return JwtAuthFilter(jwtService, userDetailService)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(userDetailService)
            .passwordEncoder(encoder())
    }

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/api/**")
                    .hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/auth/**")
                    .hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/auth/**")
                    .permitAll()
                .antMatchers("/auth/logout")
                    .authenticated()
            .anyRequest()
                .authenticated()
            .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }
}