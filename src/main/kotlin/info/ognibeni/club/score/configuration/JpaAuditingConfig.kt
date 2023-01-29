package info.ognibeni.club.score.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

/**
 * Enable JPA auditing
 * See also [org.springframework.data.jpa.domain.support.AuditingEntityListener]
 */
@Configuration
@EnableJpaAuditing
class JpaAuditingConfig
