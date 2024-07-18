package com.kaleiczyk.domain

import com.kaleiczyk.model.Country
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor() {

    operator fun invoke(query: String? = null): List<Country> {
        return if (query.isNullOrBlank()) {
            Country.entries
        } else {
            Country.entries.toTypedArray()
                .filter {
                    it.name.startsWith(
                        query,
                        ignoreCase = true
                    ) || it.currency.code.startsWith(query, ignoreCase = true)
                }
        }
    }
}