package info.ognibeni.club.score.usecase.concert.api.model

import info.ognibeni.club.score.usecase.concert.domain.Concert
import info.ognibeni.club.score.usecase.concert.domain.ConcertScore
import info.ognibeni.club.score.usecase.score.api.model.ApiScore
import info.ognibeni.club.score.usecase.score.api.model.toApi

fun ConcertScore.toApi(): ApiScore =
	score.toApi()

fun Concert.toApi(): ApiConcert =
	ApiConcert(
		name = name,
		date = date,
		location = location,
		scores = scores
			.sortedBy { it.sequenceNumber }
			.map { it.toApi() })
