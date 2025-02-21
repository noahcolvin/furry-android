package com.example.android.furry.util

fun formatPrice(price: Double): String {
    return "$${"%.2f".format(price)}"
}

fun allIfNullRemoveTrailingS(category: String?): String {
    return when (category) {
        null -> "All"
        else -> category.removeSuffix("s")
    }
}