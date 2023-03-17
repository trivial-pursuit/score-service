package info.ognibeni.club.score.usecase.score.persistence

import info.ognibeni.club.score.usecase.score.domain.Score
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ScoreRepository : JpaRepository<Score, Long> {
	fun getByScoreNumber(scoreNumber: Int): Score
}
