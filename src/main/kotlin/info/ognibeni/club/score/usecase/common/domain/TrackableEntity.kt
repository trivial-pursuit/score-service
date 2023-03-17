package info.ognibeni.club.score.usecase.common.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class TrackableEntity : BaseEntity(), Persistable<Long> {

	/** Creation timestamp, set automatically by [AuditingEntityListener]. */
	// TODO: Should it be @field:CreatedDate ?
	@CreatedDate
	@Column(nullable = false, updatable = false)
	var createdAt: ZonedDateTime? = null

	/** Timestamp of last modification, set automatically by [AuditingEntityListener]. */
	// TODO: Should it be @field:LastModifiedDate ?
	@LastModifiedDate
	@Column(nullable = false)
	var modifiedAt: ZonedDateTime? = null

	@Version
	@Column(nullable = false)
	private val version: Int? = null

	override fun getId(): Long = id

	override fun isNew(): Boolean = version == null
}
