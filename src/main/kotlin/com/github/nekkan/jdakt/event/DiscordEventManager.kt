package com.github.nekkan.jdakt.event

import com.github.nekkan.jdakt.client.DiscordClient
import kotlinx.coroutines.ObsoleteCoroutinesApi

class DiscordEventManager @ObsoleteCoroutinesApi constructor(val client: DiscordClient) {

    var events = listOf<DiscordEvent<*>>()
        private set

    fun add(vararg elmnts: DiscordEvent<*>) = elmnts.forEach { e ->
        events = events.toMutableList().apply { this.add(e) }.toList()
    }

}