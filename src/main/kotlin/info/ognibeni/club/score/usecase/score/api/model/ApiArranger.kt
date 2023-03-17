package info.ognibeni.club.score.usecase.score.api.model

import java.time.LocalDate

data class ApiArranger(

	val firstName: String?,

	val lastName: String,

	val dayOfBirth: LocalDate?,

	val dayOfDeath: LocalDate?
)
