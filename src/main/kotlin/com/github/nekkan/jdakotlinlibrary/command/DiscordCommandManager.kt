package com.github.nekkan.jdakotlinlibrary.command

import com.github.nekkan.jdakotlinlibrary.client.DiscordClient

class DiscordCommandManager(val client: DiscordClient) {

    var commands = mapOf<String, DiscordCommand>()
    private set

    var aliases = mapOf<String, String>()
    private set

    fun add(vararg elmnts: DiscordCommand) = elmnts.forEach { e ->
        commands = commands.toMutableMap().apply { this[e.settings.name.toLowerCase()] = e }.toMap()
        e.settings.aliases?.let {
            it.forEach {
                aliases = aliases.toMutableMap().apply { this[it.toLowerCase()] = e.settings.name }.toMap()
            }
        }
    }

}