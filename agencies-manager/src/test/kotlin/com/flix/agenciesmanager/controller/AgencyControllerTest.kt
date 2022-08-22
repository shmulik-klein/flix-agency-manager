package com.flix.agenciesmanager.controller

import com.flix.agenciesmanager.model.Agency
import com.flix.agenciesmanager.service.AgencyRepository
import com.flix.agenciesmanager.service.AgencyService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@EnableAutoConfiguration(exclude = [MongoAutoConfiguration::class, MongoDataAutoConfiguration::class])
@AutoConfigureMockMvc
internal class AgencyControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
) {
    @MockkBean
    lateinit var agencyService: AgencyService

    @MockBean
    lateinit var agencyRepository: AgencyRepository

    @Test
    fun `should return all agencies`() {
        every { agencyService.getAllAgencies() } returns emptyList()
        mockMvc.get("/api/v1/agencies").andDo { print() }.andExpect {
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
            val agencyRequest = Agency(
                id = null, "The Corner", "United Kingdom", "GBR",
                "London", "Batty Street E1", "GBP", "Mister Butter Cup"
            )
            val mockAgency = Agency(
                "6302d2ec004a8f3bfe2e3a66", "The Corner", "United Kingdom", "GBR",
                "London", "Batty Street E1", "GBP", "Mister Butter Cup"
            )
            every { agencyService.createAgency(agencyRequest) }.returns(mockAgency)
            val agencyJson = """
                {"name":"The Corner","country":"United Kingdom","countryCode":"GBR",
                "city":"London","street":"Batty Street E1","currency":"GBP","contact":"Mister Butter Cup"}
            """.trimIndent()
            mockMvc.post("/api/v1/agencies/") {
                contentType = MediaType.APPLICATION_JSON
                content = agencyJson
            }.andDo { print() }.andExpect {
                status { isCreated() }
                content { MediaType.APPLICATION_JSON }
                jsonPath("$.id") { value("6302d2ec004a8f3bfe2e3a66") }
            }
        }
    }

    @Nested
    @DisplayName("PUT /api/v1/agencies/{id}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class UpdateAgencyTests {

        @Test
        fun `given valid agency should return the updated`() {
            val mockAgency = Agency(
                "6302d2ec004a8f3bfe2e3a66", "The Corner", "United Kingdom", "GBR",
                "London", "Batty Street E1", "GBP", "Mister Butter Cup"
            )
            every { agencyService.updateAgency(mockAgency) }.returns(mockAgency)
            val agencyJson = """
                {"id": "6302d2ec004a8f3bfe2e3a66", "name":"The Corner","country":"United Kingdom","countryCode":"GBR",
                "city":"London","street":"Batty Street E1","currency":"GBP","contact":"Mister Butter Cup"}
            """.trimIndent()
            mockMvc.put("/api/v1/agencies/6302d2ec004a8f3bfe2e3a66") {
                contentType = MediaType.APPLICATION_JSON
                content = agencyJson
            }.andDo { print() }.andExpect {
                status { isOk() }
                content { MediaType.APPLICATION_JSON }
                jsonPath("$.id") { value("6302d2ec004a8f3bfe2e3a66") }
            }
        }

        @Test
        fun `given an agency with invalid property should return 400 status code`() {
            val mockAgency = Agency(
                "6302d2ec004a8f3bfe2e3a66", "The Corner", "United Kingdom", "GBR",
                "London", "Batty Street E1", "GBP", "Mister Butter Cup"
            )
            val errorMsg = "Invalid Country Code: GER"
            every { agencyService.updateAgency(mockAgency) }.throws(IllegalArgumentException(errorMsg))
            val agencyJson = """
                {"id": "6302d2ec004a8f3bfe2e3a66", "name":"The Corner","country":"United Kingdom","countryCode":"GBR",
                "city":"London","street":"Batty Street E1","currency":"GBP","contact":"Mister Butter Cup"}
            """.trimIndent()
            val result = mockMvc.put("/api/v1/agencies/6302d2ec004a8f3bfe2e3a66") {
                contentType = MediaType.APPLICATION_JSON
                content = agencyJson
            }
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    content { MediaType.APPLICATION_JSON }
                }
                .andReturn()
            Assertions.assertEquals(result.response.contentAsString, errorMsg)
        }
    }

    @Nested
    @DisplayName("DELETE /api/v1/agencies/{id}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteAgencyTests {

        @Test
        fun `delete the provided agency`() {
            val id = "6302d2ec004a8f3bfe2e3a66"
            every { agencyService.removeAgency(id) } just Runs
            mockMvc.delete("/api/v1/agencies/${id}") {
                contentType = MediaType.APPLICATION_JSON
            }.andDo { print() }.andExpect {
                status { isOk() }
                content { MediaType.APPLICATION_JSON }
            }
        }
    }
}