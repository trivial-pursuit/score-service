package info.ognibeni.club.score.usecase.score

import info.ognibeni.club.score.usecase.score.domain.Score
import info.ognibeni.club.score.usecase.score.persistence.ScoreRepository
import org.springframework.stereotype.Service

@Service
class RetrieveScoreUseCase(private val scoreRepository: ScoreRepository) {

	fun fetchAll(): List<Score> =
		scoreRepository.findAll()
}
