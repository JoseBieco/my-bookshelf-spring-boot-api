package com.example.demo.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
class WebSecurityConfig(
    @Autowired
    val userDetailService: UserDetailsServiceImpl
): WebSecurityConfigurerAdapter() {

    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.`$2A`)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(userDetailService)
            .passwordEncoder(encoder())

        /*auth.inMemoryAuthentication().passwordEncoder(encoder())
            .withUser("jose").password(encoder().encode("13072001")).roles("USER")*/
    }

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/**").hasAnyRole("ADMIN", "USER")
            .antMatchers(HttpMethod.DELETE, "/auth/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.POST, "/auth/**").permitAll()
            .antMatchers("/auth/logout").authenticated()
            .anyRequest().authenticated()
            .and()
            .httpBasic()

    }
}