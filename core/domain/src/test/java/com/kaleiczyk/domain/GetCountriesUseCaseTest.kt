package com.kaleiczyk.domain

import com.kaleiczyk.model.Country
import org.junit.Assert
import org.junit.Test

class GetCountriesUseCaseTest {

    private val usecase = GetCountriesUseCase()

    @Test
    fun test_should_return_all_countries() {
        val result = usecase()
        Assert.assertEquals(Country.entries.toList(), result)
    }

    @Test
    fun test_search_countries() {
        val query = "p"
        val result = usecase(query)
        Assert.assertEquals(
            Country.entries.filter { it.name.startsWith(query, ignoreCase = true) },
            result
        )
    }
}