package info.ognibeni.club.score.rest

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@Hidden
@RestController
class SwaggerUiRedirectController {

	@GetMapping("/")
	fun swaggerUI() = RedirectView("/swagger-ui/index.html")
}
