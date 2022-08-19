package com.flix.agenciesmanager

import com.flix.agenciesmanager.model.Agency
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Disabled
@Testcontainers
@DataMongoTest
internal class AgencyServiceTest @Autowired constructor(
    private val agencyRepository: AgencyRepository, private val mongoTemplate: MongoTemplate
) {
    private lateinit var service: AgencyService

    companion object {
        @JvmStatic
        @Container
        val mongoDB = MongoDBContainer("mongo:latest")

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            mongoDB.start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.host", mongoDB::getHost)
            registry.add("spring.data.mongodb.port", mongoDB::getFirstMappedPort)
        }
    }

    @BeforeEach
    fun setup() {
        mongoTemplate.dropCollection("agency")
        mongoTemplate.createCollection("agency")
        service = AgencyService(agencyRepository)
    }

    @Test
    fun `should throw an exception when the country code is invalid`() {
        val agency = Agency(id = null, "a", "a", "IllegalCountryCode", "a", "a", "a", "a")
        val e: IllegalArgumentException = assertThrows { service.createAgency(agency) }
        Assertions.assertThat(e).hasMessageContaining("The agency has an illegal country code: IllegalCountryCode")
    }

    @Test
    fun `all`() {
        Assertions.assertThat(service.getAllAgencies()).isEmpty()
    }


    @Test
    fun `mongo`() {
        val agency = Agency(null, "a", "a", "FRA", "a", "a", "a", "a")
        mongoTemplate.insert(agency, "agency")
        val service = AgencyService(agencyRepository)
        Assertions.assertThat(service.getAllAgencies().size).isEqualTo(1)
        Assertions.assertThat(service.getAllAgencies().stream().allMatch { it.id != null })
    }
}