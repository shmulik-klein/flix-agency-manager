package com.flix.agenciesmanager

import com.flix.agenciesmanager.model.Agency
import org.springframework.data.mongodb.repository.MongoRepository

interface AgencyRepository : MongoRepository<Agency, String> {}