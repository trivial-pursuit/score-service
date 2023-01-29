package info.ognibeni.club.score.usecase.score.domain

import info.ognibeni.club.score.usecase.common.domain.TrackableEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "parts")
class Part(

	@Column(nullable = false)
	val name: String,

	@Column(nullable = true)
	val description: String?

) : TrackableEntity() {

	@Override
	override fun toString(): String =
		toString("name")
}
