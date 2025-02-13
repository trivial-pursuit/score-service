package info.ognibeni.club.score.usecase.concert.api.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Concerts", description = "List of concerts")
data class ApiConcerts(

	@Schema(required = true, description = "The list of concerts")
	val concerts: List<ApiConcert>
)
