package info.ognibeni.club.score.usecase.score

import info.ognibeni.club.score.usecase.score.domain.Score
import info.ognibeni.club.score.usecase.score.domain.ScoreNumber

object Fixtures {
	fun exampleScore(number: Int = 1, title: String = "Example Title"): Score =
			Score(scoreNumber = ScoreNumber(number),
					title = title,
					description = null,
					composer = null,
					arranger = null,
					publisher = null,
					parts = emptySet())
}
