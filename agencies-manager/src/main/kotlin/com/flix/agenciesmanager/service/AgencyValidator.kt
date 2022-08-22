package com.flix.agenciesmanager.service

import com.flix.agenciesmanager.model.Agency
import java.util.*

/**
 * A validator for the [com.flix.agenciesmanager.model.Agency] objects
 */
class AgencyValidator {
    companion object {
        val validCountryCodes = Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA3)!!

        /**
         * Validates the provides agency. If the agency isn't valid, a proper exception will be thrown.
         *
         * @throws IllegalArgumentException if the agency has an invalid property or state
         */
        fun validate(agency: Agency) {
            validateCountryCode(agency)
            validateCurrency(agency)
        }

        private fun validateCurrency(agency: Agency) {
            try {
                Currency.getInstance(agency.currency)
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Invalid currency: ${agency.currency}")
            }
        }

        private fun validateCountryCode(agency: Agency) {
            if (!validCountryCodes.contains(agency.countryCode)) {
                throw IllegalArgumentException("Invalid country code: ${agency.countryCode}")
            }
        }
    }
}