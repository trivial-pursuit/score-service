package info.ognibeni.club.score.usecase.common.domain

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

@MappedSuperclass
open class BaseEntity(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0L
) {

	// See details here https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (!javaClass.isInstance(other)) return false
		other as BaseEntity

		return id == other.id
	}

	override fun hashCode(): Int = javaClass.hashCode()

	// TODO: Crazy reflection magic setting accessibility always to true: should be refactored
	fun toString(vararg properties: String): String {
		val propertyPairs = this::class.declaredMemberProperties
			.filter { properties.contains(it.name) }
			.onEach { it.isAccessible = true }
			.joinToString { it.name + " = " + it.getter.call(this).toString() }
		return this::class.simpleName + "(id = $id, $propertyPairs)"
	}
}
