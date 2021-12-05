package com.example.demo.configuration.jwt

import com.example.demo.user.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class JwtService(
    @Value("\${security.jwt.expiration}")
    private val expiration: String,

    @Value("\${security.jwt.signatureKey}")
    private val signatureKey: String
) {

    fun generateToken(user: User): String {
        val expirationTime: LocalDateTime = LocalDateTime.now().plusMinutes(expiration.toLong())
        val date = Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant())

        return Jwts
                .builder()
                .setSubject(user.email)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, signatureKey)
                .compact()
    }

    @Throws(ExpiredJwtException::class)
    fun getClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(signatureKey).parseClaimsJws(token).body
    }

    fun validToken(token: String): Boolean {
        try {
            val claims: Claims = getClaims(token)
            val date: LocalDateTime = claims.expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()

            return !LocalDateTime.now().isAfter(date)
        }catch (e: Exception){
            return false
        }
    }

    @Throws(ExpiredJwtException::class)
    fun getUserLogin(token: String): String {
        return getClaims(token).subject
    }
}









