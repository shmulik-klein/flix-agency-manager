package com.flix.agenciesmanager.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.flix.agenciesmanager.AgencyRepository
import com.flix.agenciesmanager.AgencyService
import com.flix.agenciesmanager.model.Agency
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.shaded.org.hamcrest.Matchers
import java.util.*
import java.util.regex.Matcher

@Disabled
@SpringBootTest
@EnableAutoConfiguration(exclude = [MongoAutoConfiguration::class, MongoDataAutoConfiguration::class])
@AutoConfigureMockMvc
internal class AgencyControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    @MockBean private val agencyService: AgencyService
) {

    @Test
    fun `should return all agencies`() {
        mockMvc.get("/api/agencies").andDo { print() }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
    }

    @Nested
    @DisplayName("POST /api/v1/agencies")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class CreateAgencyTests {

        @Test
        fun `given valid agency should return the generated`() {
            val agencyJson = """
                {"name":"The Corner","country":"United Kingdom","countryCode":"GBR",
                "city":"London","street":"Batty Street E1","currency":"GBP","contact":"Mister Butter Cup"}
            """.trimIndent()
            mockMvc.post("/api/agencies/") {
                contentType = MediaType.APPLICATION_JSON
                content = agencyJson
            }.andDo { print() }.andExpect {
                status { isCreated() }
                content { MediaType.APPLICATION_JSON }
                jsonPath("$.id") { isNotEmpty() }
            }
        }
    }
}