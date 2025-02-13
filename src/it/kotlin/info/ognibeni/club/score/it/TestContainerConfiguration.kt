package info.ognibeni.club.score.it

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait

/**
 * Interface that should be used for all integration tests needing a test container.
 *
 * For tests derived by this class, a PostgreSQL instance within a Docker container will be
 * started the first time, when this base class [TestContainerConfiguration] is loaded (e.g.
 * for the first IT). The container will not be destroyed up until the JVM exits.
 *
 * As a result, tests will run much faster, compared to starting a fresh Docker container for
 * each test class.
 *
 * More information can be found here:
 * [https://rieckpil.de/testing-spring-boot-applications-with-kotlin-and-testcontainers/]
 */
interface TestContainerConfiguration {
	companion object {

		private const val POSTGRESQL_VERSION = "17"

		/**
		 * Note that this field is defined within the companion object so that it will only start once
		 * for all tests in the current JVM, as opposed to once for every test class (via @BeforeAll).
		 */
		private val postgreSqlContainer = PostgreSQLContainer<Nothing>("postgres:$POSTGRESQL_VERSION").apply {
			withDatabaseName("score-test")
			withUsername("admin")
			withPassword("pass")
			waitingFor(Wait.forListeningPort())
			start()
		}

		/**
		 * The Docker container will expose a dynamic JDBC URL for PostgreSQL. Here we make sure the JDBC
		 * properties are correctly injected into Spring's [DynamicPropertyRegistry].
		 */
		@JvmStatic
		@DynamicPropertySource
		fun properties(registry: DynamicPropertyRegistry) {
			registry.add("spring.datasource.url", postgreSqlContainer::getJdbcUrl)
		}
	}
}
