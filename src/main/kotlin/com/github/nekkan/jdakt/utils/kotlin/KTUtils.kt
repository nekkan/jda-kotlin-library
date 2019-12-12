package com.github.nekkan.jdakt.utils.kotlin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun List<String>.containsIgnoreCase(text: String): Boolean {
    val exists = filter { it.equals(text, true) }
    return exists.isNotEmpty()
}

fun repeatingTask(amount: Int, delayInMillis: Long, block: CoroutineScope.() -> Unit) = runBlocking {
    launch {
        repeat(amount) {
            block(this)
            delay(delayInMillis)
        }
    }
}

fun await(millis: Long) = runBlocking { delay(millis) }