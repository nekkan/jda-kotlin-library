package com.github.nekkan.jdakt.utils.kotlin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun List<String>.containsIgnoreCase(text: String): Boolean = stream().anyMatch { x: String -> x.equals(text, ignoreCase = true) }

fun repeatingTask(amount: Int, delayInMillis: Long, block: CoroutineScope.() -> Unit) = runBlocking {
    launch {
        repeat(amount) {
            block(this)
            delay(delayInMillis)
        }
    }
}



fun pause(millis: Long) = runBlocking { delay(millis) }