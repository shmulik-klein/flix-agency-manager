package com.flix.agenciesmanager.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Represents a Flix agency
 *
 * @param id internal identifier of the agency in the application
 * @param name name of the agency
 * @param country name of the country in which the agency resides
 * @param countryCode an ISO3166-1 alpha-3 three letter country code
 * @param city name of the city in which the agency resides
 * @param street name of the street in which the agency resides
 * @param currency currency code (ISO 4217) to apply to the settlement
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