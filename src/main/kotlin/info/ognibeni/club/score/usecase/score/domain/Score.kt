package info.ognibeni.club.score.usecase.score.domain

import info.ognibeni.club.score.usecase.common.domain.TrackableEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.EAGER
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "scores")
class Score(

	@Column(nullable = false)
	val scoreNumber: ScoreNumber,

	@Column(nullable = false)
	val title: String,

	@Column(nullable = true)
	val description: String?,

	@ManyToOne
	@JoinColumn(name = "composer_id", nullable = true)
	val composer: Composer?,

	@ManyToOne
	@JoinColumn(name = "arranger_id", nullable = true)
	val arranger: Arranger?,

	@Column(nullable = true)
	val publisher: String?,

//	@ManyToMany(cascade = [PERSIST, MERGE], fetch = EAGER)
//	@JoinTable(name = "score_part",
//		joinColumns = [JoinColumn(name = "score_id")],
//		inverseJoinColumns = [JoinColumn(name = "part_id")])
	@OneToMany(mappedBy = "score", fetch = EAGER)
	val parts: Set<ScorePart>

) : TrackableEntity() {

	@Override
	override fun toString(): String =
		toString("scoreNumber", "title")
}
