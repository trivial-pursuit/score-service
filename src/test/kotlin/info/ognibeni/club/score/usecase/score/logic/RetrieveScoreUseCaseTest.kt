package info.ognibeni.club.score.usecase.score.logic

import info.ognibeni.club.score.usecase.score.Fixtures.exampleScore
import info.ognibeni.club.score.usecase.score.domain.ScoreNumber
import info.ognibeni.club.score.usecase.score.exception.ScoreNotFoundException
import info.ognibeni.club.score.usecase.score.persistence.ScoreRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.dao.EmptyResultDataAccessException

class RetrieveScoreUseCaseTest {

	private val scoreRepository: ScoreRepository = mockk()

	private val sut = RetrieveScoreUseCase(scoreRepository)

	@Test
	fun `retrieving multiple scores succeeds`() {
		val expectedScores = listOf(
				exampleScore(1, "Example Score 1"),
				exampleScore(2, "Example Score 2"))

		every { scoreRepository.findAll() } returns expectedScores

		val scores = sut.getAllScores()

		verify(exactly = 1) { scoreRepository.findAll() }
		assertThat(scores)
				.hasSize(expectedScores.size)
				.containsAll(expectedScores)
	}

	@Test
	fun `retrieving an empty score list succeeds`() {
		every { scoreRepository.findAll() } returns emptyList()

		val scores = sut.getAllScores()

		verify(exactly = 1) { scoreRepository.findAll() }
		assertThat(scores)
				.isEmpty()
	}

	@Test
	fun `retrieving an existing score succeeds`() {
		val expectedScore = exampleScore()
		every { scoreRepository.getByScoreNumber(any()) } returns expectedScore

		val score = sut.getScore(ScoreNumber(1))

		verify(exactly = 1) { scoreRepository.getByScoreNumber(1) }
		assertThat(score)
			.isEqualTo(expectedScore)
	}

	@Test
	fun `retrieving a non-existing score fails`() {
		every { scoreRepository.getByScoreNumber(any()) } throws EmptyResultDataAccessException(1)

		assertThrows<ScoreNotFoundException> {
			sut.getScore(ScoreNumber(1))
		}

		verify(exactly = 1) { scoreRepository.getByScoreNumber(1) }
	}
}
