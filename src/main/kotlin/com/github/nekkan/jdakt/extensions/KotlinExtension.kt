@file:JvmName("KotlinExtension")
package com.github.nekkan.jdakt.extensions

import kotlinx.coroutines.newSingleThreadContext
import kotlin.random.Random

@UseExperimental(kotlinx.coroutines.ObsoleteCoroutinesApi::class)
fun thread() = newSingleThreadContext("new-thread-"+ Random.nextInt(9569))