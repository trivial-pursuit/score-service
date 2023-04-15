package info.ognibeni.club.score.usecase.concert.api

import info.ognibeni.club.score.usecase.concert.Fixtures.exampleConcert
import info.ognibeni.club.score.usecase.concert.Fixtures.exampleConcertScore
import info.ognibeni.club.score.usecase.concert.api.model.ApiConcert
import info.ognibeni.club.score.usecase.concert.api.model.toApi
import info.ognibeni.club.score.usecase.concert.domain.Concert
import info.ognibeni.club.score.usecase.concert.domain.ConcertScore
import info.ognibeni.club.score.usecase.score.Fixtures.exampleScore
import info.ognibeni.club.score.usecase.score.api.model.ApiArranger
import info.ognibeni.club.score.usecase.score.api.model.ApiComposer
import info.ognibeni.club.score.usecase.score.api.model.ApiPart
import info.ognibeni.club.score.usecase.score.api.model.ApiScore
import info.ognibeni.club.score.usecase.score.domain.Arranger
import info.ognibeni.club.score.usecase.score.domain.Composer
import info.ognibeni.club.score.usecase.score.domain.Part
import info.ognibeni.club.score.usecase.score.domain.Score
import info.ognibeni.club.score.usecase.score.domain.ScoreNumber
import info.ognibeni.club.score.usecase.score.domain.ScorePart
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class MapperTest {

	@Test
	fun `maps concert score correctly`() {
		val concertScore = ConcertScore(
			exampleConcert(),
			Score(
				ScoreNumber(1),
				"Score title",
				"Score description",
				Composer("John", "Doe", LocalDate.MIN, LocalDate.MAX),
				Arranger("Jane", "Doe", LocalDate.MIN, LocalDate.MAX),
				"Score publisher",
				setOf(ScorePart(
					exampleScore(),
					Part("Part name", "Part description"),
					analog = true,
					digital = false))),
			1)

		val expectedApiScore =
			ApiScore(1,
				"Score title",
				"Score description",
				ApiComposer("John", "Doe", LocalDate.MIN, LocalDate.MAX),
				ApiArranger("Jane", "Doe", LocalDate.MIN, LocalDate.MAX),
				"Score publisher",
				listOf(ApiPart(
					"Part name",
					"Part description",
					analog = true,
					digital = false)))

		assertThat(concertScore.toApi())
				.isEqualTo(expectedApiScore)
	}

	@Test
	fun `maps concert correctly`() {
		val concert = Concert(
			"Concert name",
			LocalDate.MIN,
			"Concert location",
			setOf(
				exampleConcertScore(exampleConcert(), exampleScore(2, "Example Score 2"), sequenceNumber = 2),
				exampleConcertScore(exampleConcert(), exampleScore(3, "Example Score 3"), sequenceNumber = 3),
				exampleConcertScore(exampleConcert(), exampleScore(1, "Example Score 1"), sequenceNumber = 1)
			))

		val expectedApiConcert =
				ApiConcert(
					"Concert name",
					LocalDate.MIN,
					"Concert location",
					listOf(
						ApiScore(1, "Example Score 1", null, null, null, null, emptyList()),
						ApiScore(2, "Example Score 2", null, null, null, null, emptyList()),
						ApiScore(3, "Example Score 3", null, null, null, null, emptyList())
					)
				)

		assertThat(concert.toApi())
				.isEqualTo(expectedApiConcert)
	}
}
