package com.github.nekkan.jdakotlinlibrary.event

import com.github.nekkan.jdakotlinlibrary.client.DiscordClient
import com.github.nekkan.jdakotlinlibrary.client.DiscordResult
import kotlinx.coroutines.ObsoleteCoroutinesApi
import net.dv8tion.jda.api.events.GenericEvent
import kotlin.reflect.KClass

@ObsoleteCoroutinesApi
abstract class DiscordEvent {

    var events = mapOf<String, (GenericEvent, DiscordClient) -> DiscordResult>()
        private set

    fun _____________addEvent(c: KClass<GenericEvent>, event: (GenericEvent, DiscordClient) -> DiscordResult) {
        events = events.toMutableMap().apply { put(c.qualifiedName!!, event) }.toMap()
    }

    inline fun <reified T: GenericEvent> event(noinline event: (T, DiscordClient) -> DiscordResult) {
        _____________addEvent(T::class as KClass<GenericEvent>, event as (GenericEvent, DiscordClient) -> DiscordResult)
    }


}