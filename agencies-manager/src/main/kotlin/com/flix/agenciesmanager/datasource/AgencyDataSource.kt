package com.flix.agenciesmanager.datasource

import com.flix.agenciesmanager.model.Agency

interface AgencyDataSource {
    fun getAgencies(): Collection<Agency>
}