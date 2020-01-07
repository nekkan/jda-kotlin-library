package com.github.nekkan.jdakotlinlibrary.event

import com.github.nekkan.jdakotlinlibrary.client.DiscordClient
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
class DiscordEventManager @ObsoleteCoroutinesApi constructor(val client: DiscordClient) {


    var events = listOf<DiscordEvent>()
        private set

    fun add(vararg elmnts: DiscordEvent) = elmnts.forEach { e ->
        events = events.toMutableList().apply { this.add(e) }.toList()
    }

}