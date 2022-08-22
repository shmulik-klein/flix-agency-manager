package com.flix.agenciesmanager.service

import com.flix.agenciesmanager.model.Agency
import org.springframework.stereotype.Service
import java.util.*


@Service
class AgencyService(val agencyRepository: AgencyRepository) {

    /**
     * Returns all the agencies, if no agency was created an empty collection will be returned
     */
    fun getAllAgencies(): Collection<Agency> = agencyRepository.findAll()

    /**
     * Creates a new agency in the application
     *
     * @param agency a new agency to create
     * @throws IllegalArgumentException if the new agency has an invalid property
     */
    fun createAgency(agency: Agency): Agency {
        AgencyValidator.validate(agency)
        return agencyRepository.save(agency)
    }

    /**
     * Removes an agency from the application
     *
     * @param id the id of the agency to remove
     */
    fun removeAgency(id: String) = agencyRepository.deleteById(id)

    /**
     * Updates an existing agency, without changing its id
     *
     * @param agency the new agency to update
     * @throws IllegalArgumentException if the updated agency has an invalid property
     */
    fun updateAgency(agency: Agency): Agency {
        AgencyValidator.validate(agency)
        return agencyRepository.save(agency)
    }

    /**
     * Returns an agency corresponded to the provided id
     *
     * @param id the id of the requested agency
     */
    fun getAgency(id: String): Optional<Agency> {
        return agencyRepository.findById(id)
    }
}