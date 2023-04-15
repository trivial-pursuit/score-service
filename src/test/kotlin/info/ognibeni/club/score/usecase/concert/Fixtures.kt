package info.ognibeni.club.score.usecase.concert

import info.ognibeni.club.score.usecase.concert.domain.Concert
import info.ognibeni.club.score.usecase.concert.domain.ConcertScore
import info.ognibeni.club.score.usecase.score.Fixtures.exampleScore
import info.ognibeni.club.score.usecase.score.domain.Score
import java.time.LocalDate

object Fixtures {
	val EXAMPLE_LOCAL_DATE: LocalDate = LocalDate.of(2023, 12,24)

	fun exampleConcert(name: String = "Example Concert"): Concert =
			Concert(name = name,
					date = EXAMPLE_LOCAL_DATE,
					location = "Example Location",
					scores = emptySet())

	fun exampleConcertScore(concert: Concert = exampleConcert(),
	                        score: Score = exampleScore(),
	                        sequenceNumber: Int = 1): ConcertScore =
		ConcertScore(concert, score, sequenceNumber, sequenceNumber.toLong())
}
