package com.github.nekkan.jdakt.command

import com.github.nekkan.jdakt.client.DiscordClient
import com.github.nekkan.jdakt.client.DiscordResult
import kotlinx.coroutines.ObsoleteCoroutinesApi
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

@ObsoleteCoroutinesApi
abstract class DiscordCommand {

    abstract val settings: DiscordCommandSettings

    abstract fun run(client: DiscordClient, arguments: List<String>, event: GuildMessageReceivedEvent): DiscordResult


    open fun commandResultErrorEvent(client: DiscordClient, arguments: List<String>, event: GuildMessageReceivedEvent) {}
    open fun commandResultSuccessEvent(client: DiscordClient, arguments: List<String>, event: GuildMessageReceivedEvent) {}
    open fun commandResultUnknownEvent(client: DiscordClient, arguments: List<String>, event: GuildMessageReceivedEvent) {}
    open fun commandResultOtherEvent(client: DiscordClient, arguments: List<String>, event: GuildMessageReceivedEvent) {}

    fun settings(s: DiscordCommandSettings.() -> Unit) = DiscordCommandSettings("",null,null,null).apply(s)

    fun command(event: GuildMessageReceivedEvent, run: GuildMessageReceivedEvent.() -> DiscordResult) = run(event)

    /*jeito 1:
    override fun run(client: DiscordClient, arguments: List<String>, event: GuildMessageReceivedEvent) = command(event) {
        reply("Oi!")
        DiscordResult.SUCCESS
    }

      jeito 2:
    override fun run(client: DiscordClient, arguments: List<String>, event: GuildMessageReceivedEvent) {
        event.reply("Oi!")
        return DiscordResult.SUCCESS
    }
    */

}