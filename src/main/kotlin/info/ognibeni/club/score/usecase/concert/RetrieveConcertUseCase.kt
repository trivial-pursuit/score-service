package info.ognibeni.club.score.usecase.concert

import info.ognibeni.club.score.usecase.concert.domain.Concert
import info.ognibeni.club.score.usecase.score.persistence.ConcertRepository
import org.springframework.stereotype.Service

@Service
class RetrieveConcertUseCase(private val concertRepository: ConcertRepository) {

	fun fetchAll(): List<Concert> =
		concertRepository.findAll()
}
