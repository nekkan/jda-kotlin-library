package com.github.nekkan.jdakt.utils.kotlin

fun List<String>.containsIgnoreCase(text: String): Boolean = stream().anyMatch { x: String -> x.equals(text, ignoreCase = true) }