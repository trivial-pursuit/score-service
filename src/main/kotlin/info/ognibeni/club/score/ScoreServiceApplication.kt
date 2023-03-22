package info.ognibeni.club.score

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class ScoreServiceApplication

fun main(args: Array<String>) {
	runApplication<ScoreServiceApplication>(*args)
}
