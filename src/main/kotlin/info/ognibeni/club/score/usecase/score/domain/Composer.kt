package info.ognibeni.club.score.usecase.score.domain

import info.ognibeni.club.score.usecase.common.domain.TrackableEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "composers")
class Composer(

	@Column(nullable = true)
	val firstName: String?,

	@Column(nullable = false)
	val lastName: String,

	@Column(nullable = true)
	val dayOfBirth: LocalDate?,

	@Column(nullable = true)
	val dayOfDeath: LocalDate?

) : TrackableEntity() {

	@Override
	override fun toString(): String =
		toString("firstName", "lastName")
}
