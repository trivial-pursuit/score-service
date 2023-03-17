package info.ognibeni.club.score.usecase.concert.domain

import info.ognibeni.club.score.usecase.common.domain.TrackableEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.EAGER
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "concerts")
class Concert(

	@Column(nullable = false)
	val name: String,

	@Column(nullable = false)
	val date: LocalDate,

	@Column(nullable = false)
	val location: String,

	@OneToMany(mappedBy = "concert", fetch = EAGER)
	val scores: Set<ConcertScore>

) : TrackableEntity() {

	@Override
	override fun toString(): String =
		toString("name", "date", "location")
}
