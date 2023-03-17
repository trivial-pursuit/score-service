package info.ognibeni.club.score.usecase.score.logic

import info.ognibeni.club.score.usecase.score.domain.Score
import info.ognibeni.club.score.usecase.score.persistence.ScoreRepository
import org.springframework.stereotype.Service

@Service
class RetrieveScoreUseCase(private val scoreRepository: ScoreRepository) {

	fun getAllScores(): List<Score> =
		scoreRepository.findAll()
}
