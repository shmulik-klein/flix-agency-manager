package com.flix.agenciesmanager.service

import com.flix.agenciesmanager.model.Agency
import org.springframework.data.mongodb.repository.MongoRepository

interface AgencyRepository : MongoRepository<Agency, String> {}