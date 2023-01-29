package info.ognibeni.club.score.usecase.score.domain

import info.ognibeni.club.score.usecase.common.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "score_part")
class ScorePart(

	@ManyToOne
	@JoinColumn(name = "score_id")
	private val score: Score,

	@ManyToOne
	@JoinColumn(name = "part_id")
	val part: Part,

	@Column(nullable = false)
	val analog: Boolean,

	@Column(nullable = false)
	val digital: Boolean
) : BaseEntity() {

	@Override
	override fun toString(): String =
		toString("score", "part", "analog", "digital")
}
