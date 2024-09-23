package com.example.tleapplication.support.security

import com.example.tleapplication.domain.auth.TokenAuthService
import com.example.tleapplication.support.response.CommonResponseCode
import com.example.tleapplication.support.response.ErrorResponse
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.io.DecodingException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class JwtAuthenticationFilter(
    private val objectMapper: ObjectMapper,
    private val tokenAuthService: TokenAuthService,
) : OncePerRequestFilter() {
    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            val token: String? = request.getHeader("Authorization")
            tokenAuthService.authenticate(token)

            filterChain.doFilter(request, response)
        } catch (e: SignatureException) {
            sendErrorMessage(response, CommonResponseCode.COMMON_01)
        } catch (e: MalformedJwtException) {
            sendErrorMessage(response, CommonResponseCode.COMMON_01)
        } catch (e: DecodingException) {
            sendErrorMessage(response, CommonResponseCode.COMMON_01)
        } catch (e: ExpiredJwtException) {
            sendErrorMessage(response, CommonResponseCode.COMMON_01)
        }
    }

    @Throws(IOException::class)
    private fun sendErrorMessage(
        res: HttpServletResponse,
        error: String,
    ) {
        res.status = HttpServletResponse.SC_UNAUTHORIZED
        res.contentType = MediaType.APPLICATION_JSON.toString()
        res.characterEncoding = "UTF-8"

        val dto = ErrorResponse(error)
        val response = objectMapper.writeValueAsString(dto)

        res.writer.write(response)
    }
}