package info.ognibeni.club.score.usecase.score.api

import info.ognibeni.club.score.usecase.score.Fixtures.exampleScore
import info.ognibeni.club.score.usecase.score.api.model.ApiArranger
import info.ognibeni.club.score.usecase.score.api.model.ApiComposer
import info.ognibeni.club.score.usecase.score.api.model.ApiPart
import info.ognibeni.club.score.usecase.score.api.model.ApiScore
import info.ognibeni.club.score.usecase.score.api.model.toApi
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
	fun `maps arranger correctly`() {
		val arranger =
				Arranger("John", "Doe", LocalDate.MIN, LocalDate.MAX)

		val expectedApiArranger =
				ApiArranger("John", "Doe", LocalDate.MIN, LocalDate.MAX)

		assertThat(arranger.toApi())
				.isEqualTo(expectedApiArranger)
	}

	@Test
	fun `maps composer correctly`() {
		val composer =
				Composer("John", "Doe", LocalDate.MIN, LocalDate.MAX)

		val expectedApiComposer =
				ApiComposer("John", "Doe", LocalDate.MIN, LocalDate.MAX)

		assertThat(composer.toApi())
				.isEqualTo(expectedApiComposer)
	}

	@Test
	fun `maps score part correctly`() {
		val scorePart = ScorePart(
				exampleScore(),
				Part("Part name", "Part description"),
				analog = true,
				digital = false)

		val expectedApiPart =
				ApiPart("Part name", "Part description", analog = true, digital = false)

		assertThat(scorePart.toApi())
				.isEqualTo(expectedApiPart)
	}

	@Test
	fun `maps score correctly`() {
		val score = Score(
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
						digital = false)))

		val expectedApiScore =
				ApiScore(1,
						"Score title",
						"Score description",
						ApiComposer("John", "Doe", LocalDate.MIN, LocalDate.MAX),
						ApiArranger("Jane", "Doe", LocalDate.MIN, LocalDate.MAX),
						"Score publisher",
						listOf(ApiPart("Part name", "Part description", analog = true, digital = false))
				)

		assertThat(score.toApi())
				.isEqualTo(expectedApiScore)
	}
}
