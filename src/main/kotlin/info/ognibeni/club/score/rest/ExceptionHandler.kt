package info.ognibeni.club.score.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

	@ExceptionHandler(Exception::class)
	fun handleAllRemainingExceptions(ex: Exception, request: WebRequest): ResponseEntity<Any> {
		return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
	}
}
