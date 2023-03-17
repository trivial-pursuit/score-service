package info.ognibeni.club.score.usecase.concert.persistence

import info.ognibeni.club.score.usecase.concert.domain.Concert
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConcertRepository : JpaRepository<Concert, Long>
