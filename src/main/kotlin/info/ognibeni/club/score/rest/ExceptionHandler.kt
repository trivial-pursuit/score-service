package info.ognibeni.club.score.rest

import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/**
 * Using [ResponseEntityExceptionHandler] turns on Spring's application/problem+json support
 * @see org.springframework.http.ProblemDetail
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc7807">RFC 7807</a>
 */
@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler()
