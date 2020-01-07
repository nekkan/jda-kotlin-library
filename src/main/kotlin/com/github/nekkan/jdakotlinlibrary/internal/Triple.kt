package com.github.nekkan.jdakotlinlibrary.internal

import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
class Triple<A, B, C>(a: A?, b: B?, c: C?) {

    var first: A? = a
    var second: B? = b
    var third: C? = c

}