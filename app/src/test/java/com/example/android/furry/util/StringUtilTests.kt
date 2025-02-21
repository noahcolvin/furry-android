package com.example.android.furry.util

import org.junit.Test

class StringUtilsTests {
    @Test
    fun formatPrice_typicalValue() {
        val price = 10.0
        val formattedPrice = formatPrice(price)
        assert(formattedPrice == "$10.00")
    }

    @Test
    fun formatPrice_long_decimal() {
        val price = 10.0123456789
        val formattedPrice = formatPrice(price)
        assert(formattedPrice == "$10.01")
    }

    @Test
    fun allIfNullRemoveTrailingS_null() {
        val category = null
        val formattedCategory = allIfNullRemoveTrailingS(category)
        assert(formattedCategory == "All")
    }

    @Test
    fun allIfNullRemoveTrailingS_typicalValue() {
        val category = "Cats"
        val formattedCategory = allIfNullRemoveTrailingS(category)
        assert(formattedCategory == "Cat")
    }
}