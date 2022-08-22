package com.flix.agenciesmanager.service

import com.flix.agenciesmanager.model.Agency
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

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
    fun `should return an empty collection if there are no agencies`() {
        Assertions.assertThat(service.getAllAgencies()).isEmpty()
    }

    @Test
    fun `should throw an exception when the country code is invalid`() {
        val agency =
            Agency(
                id = null, "Le Chamois", "France", "InvalidCountryCode", "Paris",
                "Rue Bonaparte 7", "EUR", "Madame Beaufort"
            )
        val e: IllegalArgumentException = assertThrows { service.createAgency(agency) }
        Assertions.assertThat(e).hasMessageContaining("Invalid country code: InvalidCountryCode")
    }

    @Test
    fun `should throw an exception when the currency is invalid`() {
        val agency =
            Agency(
                id = null, "Le Chamois", "France", "FRA", "Paris",
                "Rue Bonaparte 7", "InvalidCurrency", "Acontact"
            )
        val e: IllegalArgumentException = assertThrows { service.createAgency(agency) }
        Assertions.assertThat(e).hasMessageContaining("Invalid currency: InvalidCurrency")
    }

    @Test
    fun `should return all existing agencies`() {
        val agency = Agency(null, "Le Chamois", "France", "FRA", "Paris",
            "Rue Bonaparte 7", "EUR", "Madame Beaufort")
        mongoTemplate.insert(agency, "agency")
        val service = AgencyService(agencyRepository)
        val agencies = service.getAllAgencies()

        Assertions.assertThat(agencies).hasSize(1)
        Assertions.assertThat(agencies.stream().allMatch { it.id != null })
    }

    @Test
    fun `should return specific agency when provided id`() {
        val id = "6302d2ec004a8f3bfe2e3a66"
        val agency = Agency(id, "Le Chamois", "France", "FRA", "Paris",
            "Rue Bonaparte 7", "EUR", "Madame Beaufort")
        mongoTemplate.insert(agency, "agency")
        val service = AgencyService(agencyRepository)
        val actualAgency = service.getAgency(id)

        Assertions.assertThat(actualAgency.isPresent).isTrue
        Assertions.assertThat(actualAgency.get()).isEqualTo(agency)
    }

    @Test
    fun `should update name to new value`() {
        val agencyId = "6302d2ec004a8f3bfe2e3a66"
        val agency = Agency(agencyId, "Le Chamois", "France", "FRA", "Paris",
            "Rue Bonaparte 7", "EUR", "Madame Beaufort")
        mongoTemplate.insert(agency, "agency")
        val updatedAgency = Agency(agencyId, "Le Chamois Bavaria", "Germany", "DEU", "Paris",
            "Rue Bonaparte 7", "EUR", "Madame Beaufort")
        val service = AgencyService(agencyRepository)
        service.updateAgency(updatedAgency)

        val findDeletedQuery = Query(Criteria.where("_id").isEqualTo(agencyId))
        val agencies = mongoTemplate.find(findDeletedQuery, agency.javaClass)

        Assertions.assertThat(agencies.first()).isEqualTo(updatedAgency)
    }
    @Test
    fun `should remove agency from db when calling removeAgency`() {
        val agencyId = "6302d2ec004a8f3bfe2e3a66"
        val newAgency = Agency(agencyId, "Le Chamois", "France", "FRA", "Paris",
            "Rue Bonaparte 7", "EUR", "Madame Beaufort")
        mongoTemplate.insert(newAgency, "agency")
        val service = AgencyService(agencyRepository)
        service.removeAgency(agencyId)

        val findDeletedQuery = Query(Criteria.where("_id").isEqualTo(agencyId))
        val agencies = mongoTemplate.find(findDeletedQuery, newAgency.javaClass)

        Assertions.assertThat(agencies).isEmpty()
    }
}