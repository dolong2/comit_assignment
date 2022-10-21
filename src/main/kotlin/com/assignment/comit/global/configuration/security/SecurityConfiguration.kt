package com.assignment.comit.global.configuration.security

import com.assignment.comit.global.configuration.security.handler.CustomAccessDeniedHandler
import com.assignment.comit.global.configuration.security.handler.CustomAuthenticationEntryPointHandler
import com.assignment.comit.global.configuration.security.jwt.JwtExceptionFilter
import com.assignment.comit.global.configuration.security.jwt.JwtReqFilter
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

@Component
@EnableWebSecurity
class SecurityConfiguration(
    private val jwtRequestFilter: JwtReqFilter,
    private val jwtExceptionFilter: JwtExceptionFilter,
    private val customAuthenticationEntryPointHandler: CustomAuthenticationEntryPointHandler,
    private val customAccessDeniedHandler: CustomAccessDeniedHandler,
) {
    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().antMatchers("/v1/api-docs")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/swagger-ui.html")
                .antMatchers("configuration/**")
                .antMatchers("/webjars/**")
                .antMatchers("/public")
                .and()
                .ignoring()
                .antMatchers("/h2-console/**/**")
        }
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors()
            .and()
            .csrf().disable()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests()
            .antMatchers("/v1/**").permitAll()
            .antMatchers("/v1/admin/**").hasRole("ADMIN")
            .antMatchers("/v1/member/**").hasRole("MEMBER")
            .antMatchers("/v1/posting/**").hasRole("MEMBER")
            .antMatchers(HttpMethod.GET,".posting").permitAll()
            .anyRequest().permitAll()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(customAccessDeniedHandler)
            .authenticationEntryPoint(customAuthenticationEntryPointHandler)
            .and()
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(jwtExceptionFilter, JwtReqFilter::class.java)
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(12)
    }
}