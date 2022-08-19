package com.flix.agenciesmanager.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Represents an agency
 *
 * TODO: countryCode should be refactored to IsoCountryCode.java
 * TODO: currency should be refactored to Currency.java
 */
@Document
data class Agency(
    @Id
    val id: String? = null,
    val name: String,
    val country: String,
    val countryCode: String,
    val city: String,
    val street: String,
    val currency: String,
    val contact: String
)
