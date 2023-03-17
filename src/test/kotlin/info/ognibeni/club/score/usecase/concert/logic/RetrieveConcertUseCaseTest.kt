package info.ognibeni.club.score.usecase.concert.logic

import info.ognibeni.club.score.usecase.concert.Fixtures.exampleConcert
import info.ognibeni.club.score.usecase.concert.persistence.ConcertRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RetrieveConcertUseCaseTest {

	private val concertRepository: ConcertRepository = mockk()

	private val sut = RetrieveConcertUseCase(concertRepository)

	@Test
	fun `retrieving multiple concerts succeeds`() {
		val expectedConcerts = listOf(
				exampleConcert("Example Concert 1"),
				exampleConcert("Example Concert 2"))

		every { concertRepository.findAll() } returns expectedConcerts

		val concerts = sut.getAllConcerts()

		verify(exactly = 1) { concertRepository.findAll() }
		assertThat(concerts)
				.hasSize(expectedConcerts.size)
				.containsAll(expectedConcerts)
	}

	@Test
	fun `retrieving empty concert list succeeds`() {
		every { concertRepository.findAll() } returns emptyList()

		val concerts = sut.getAllConcerts()

		verify(exactly = 1) { concertRepository.findAll() }
		assertThat(concerts)
				.isEmpty()
	}
}
