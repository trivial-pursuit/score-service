package info.ognibeni.club.score.usecase.score.logic

import info.ognibeni.club.score.usecase.score.domain.Score
import info.ognibeni.club.score.usecase.score.domain.ScoreNumber
import info.ognibeni.club.score.usecase.score.exception.ScoreNotFoundException
import info.ognibeni.club.score.usecase.score.persistence.ScoreRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class RetrieveScoreUseCase(private val scoreRepository: ScoreRepository) {

	fun getAllScores(): List<Score> =
		scoreRepository.findAll()

	fun getScore(scoreNumber: ScoreNumber): Score =
		try {
			scoreRepository.getByScoreNumber(scoreNumber.value)
		} catch (ex: EmptyResultDataAccessException) {
			throw ScoreNotFoundException(scoreNumber, ex)
		}
}
