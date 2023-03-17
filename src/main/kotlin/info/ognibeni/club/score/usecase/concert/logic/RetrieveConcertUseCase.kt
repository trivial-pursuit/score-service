package info.ognibeni.club.score.usecase.concert.logic

import info.ognibeni.club.score.usecase.concert.domain.Concert
import info.ognibeni.club.score.usecase.concert.persistence.ConcertRepository
import org.springframework.stereotype.Service

@Service
class RetrieveConcertUseCase(private val concertRepository: ConcertRepository) {

	fun getAllConcerts(): List<Concert> =
		concertRepository.findAll()
}
