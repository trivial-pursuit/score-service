package info.ognibeni.club.score.usecase.concert.domain

import info.ognibeni.club.score.usecase.common.domain.BaseEntity
import info.ognibeni.club.score.usecase.score.domain.Score
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "concert_score")
class ConcertScore(

	@ManyToOne
	@JoinColumn(name = "concert_id")
	private val concert: Concert,

	@ManyToOne
	@JoinColumn(name = "score_id")
	val score: Score,

	@Column(nullable = true)
	val sequenceNumber: Int?
) : BaseEntity() {

	@Override
	override fun toString(): String =
		toString("concert", "score", "sequenceNumber")
}
