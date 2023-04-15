package info.ognibeni.club.score.usecase.score.api.model

import info.ognibeni.club.score.usecase.score.domain.Arranger
import info.ognibeni.club.score.usecase.score.domain.Composer
import info.ognibeni.club.score.usecase.score.domain.Score
import info.ognibeni.club.score.usecase.score.domain.ScorePart

fun Arranger.toApi(): ApiArranger =
		ApiArranger(
				firstName = firstName,
				lastName = lastName,
				dayOfBirth = dayOfBirth,
				dayOfDeath = dayOfDeath)

fun Composer.toApi(): ApiComposer =
		ApiComposer(
				firstName = firstName,
				lastName = lastName,
				dayOfBirth = dayOfBirth,
				dayOfDeath = dayOfDeath)

fun ScorePart.toApi(): ApiPart =
		ApiPart(
				name = part.name,
				description = part.description,
				analog = analog,
				digital = digital)

fun Score.toApi(): ApiScore =
		ApiScore(
				number = scoreNumber.value,
				title = title,
				description = description,
				composer = composer?.toApi(),
				arranger = arranger?.toApi(),
				publisher = publisher,
				parts = parts.map { it.toApi() })
