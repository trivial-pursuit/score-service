package info.ognibeni.club.score.usecase.concert

import info.ognibeni.club.score.usecase.concert.domain.Concert
import java.time.LocalDate

object Fixtures {
	val EXAMPLE_LOCAL_DATE: LocalDate = LocalDate.of(2023, 12,24)

	fun exampleConcert(name: String = "Example Concert"): Concert =
			Concert(name = name,
					date = EXAMPLE_LOCAL_DATE,
					location = "Example Location",
					scores = emptySet())
}
