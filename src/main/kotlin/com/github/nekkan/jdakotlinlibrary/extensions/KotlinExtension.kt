@file:JvmName("KotlinExtension")
package com.github.nekkan.jdakotlinlibrary.extensions

import com.github.nekkan.jdakotlinlibrary.collections.utils.AnyMap
import kotlinx.coroutines.newSingleThreadContext
import kotlin.random.Random

@UseExperimental(kotlinx.coroutines.ObsoleteCoroutinesApi::class)
fun thread() = newSingleThreadContext("new-thread-"+ Random.nextInt(9569))

fun <T> map(a: () -> T) = AnyMap(a())

fun Any.print(nl: Boolean = true) = if(nl) println(this) else print(this)