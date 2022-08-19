package com.flix.agenciesmanager

import com.flix.agenciesmanager.model.Agency
import org.springframework.stereotype.Service
import java.util.*


@Service
class AgencyService(val agencyRepository: AgencyRepository) {
    val agencies = mutableListOf<Agency>()

    /**
     * Returns all the agencies, if no agency was created an empty collection will be returned
     */
    fun getAllAgencies(): Collection<Agency> = agencyRepository.findAll()

    /**
     * Creates a new agency.
     *
     * @param agency a new agency to create
     */
    fun createAgency(agency: Agency): Agency {
        return agencyRepository.save(agency)
    }

    fun removeAgency(id: String) = agencyRepository.deleteById(id)
    fun updateAgency(agency: Agency): Agency {
        return agencyRepository.save(agency)
    }

    fun getAgency(id: String): Optional<Agency> {
        return agencyRepository.findById(id)
    }
}