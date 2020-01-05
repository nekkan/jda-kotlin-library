package com.github.nekkan.jdakt.event

import com.github.nekkan.jdakt.client.DiscordClient
import com.github.nekkan.jdakt.client.DiscordResult
import net.dv8tion.jda.api.events.GenericEvent
import kotlin.reflect.KClass

abstract class DiscordEvent<T: GenericEvent>(var _class: KClass<T>) {

    var run: (GenericEvent, DiscordClient) -> DiscordResult = { event, client -> DiscordResult.OTHER }
    var _resultErrorEvent: (GenericEvent, DiscordClient) -> Unit = {event, client->}
    var _resultSuccessEvent: (GenericEvent, DiscordClient) -> Unit = {event, client->}
    var _resultUnknownEvent: (GenericEvent, DiscordClient) -> Unit = {event, client->}
    var _resultOtherEvent: (GenericEvent, DiscordClient) -> Unit = {event, client->}

    fun event(event: (T, DiscordClient) -> DiscordResult): DiscordResult {
        run = event as (GenericEvent, DiscordClient) -> DiscordResult
        return DiscordResult.OTHER
    }

    fun resultErrorEvent(event: T, client: DiscordClient) { _resultErrorEvent = event as (GenericEvent, DiscordClient) -> Unit }
    fun resultSuccessEvent(event: T, client: DiscordClient) { _resultSuccessEvent = event as (GenericEvent, DiscordClient) -> Unit }
    fun resultUnknownEvent(event: T, client: DiscordClient) { _resultUnknownEvent = event as (GenericEvent, DiscordClient) -> Unit }
    fun resultOtherEvent(event: T, client: DiscordClient) { _resultOtherEvent = event as (GenericEvent, DiscordClient) -> Unit }

}