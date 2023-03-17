package info.ognibeni.club.score.usecase.concert.api.model

import info.ognibeni.club.score.usecase.score.api.model.ApiScore
import java.time.LocalDate

data class ApiConcert(

	val name: String,

	val date: LocalDate,

	val location: String,

	val scores: List<ApiScore>
)
