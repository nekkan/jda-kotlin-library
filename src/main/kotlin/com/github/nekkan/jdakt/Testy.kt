package com.github.nekkan.jdakt

import com.github.nekkan.jdakt.dsl.command.command
import com.github.nekkan.jdakt.dsl.command.creator
import com.github.nekkan.jdakt.dsl.event.on
import com.github.nekkan.jdakt.utils.async.*
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class Testy {

    private val messages = mutableMapOf<Long, Message>()

    init {

        val bot = Main.jda
        val v = "✌"
        val pong = "🏓"

        val creator = creator {
            prefix = "-"
            name = "ping"
            aliases = listOf("pong")
            description = "Mostra o ping do bot."
            usage = "$prefix$name"
            extra = "Informação extra!"
        }

        command(bot, creator) { it ->
            it.channel.msg("Hi!") //Async
            it.channel.message("Hii!") //Not async
            it.channel.message("Ping!") { msg -> //Not async (message isn't async)
                msg.edit("**Pong!** *(${it.message.timeDifference(msg)}) ms*") //Async (edit & msg = async)
                msg.react(false, "✌") //Async reaction.
            }
            it.channel.sendMessage("'-'").async() //Another way to send async message.
        }

        bot.on<GuildMessageReceivedEvent> { it ->
            if(it.author.isBot) return@on
            messages[it.messageIdLong] = it.message
        }

        bot.on<GuildMessageDeleteEvent> { it ->
            if(messages[it.messageIdLong]?.contentRaw.isNullOrEmpty()) return@on
            it.channel.msg("Content: ${messages[it.messageIdLong]?.contentRaw}")
            messages.remove(it.messageIdLong)
        }
    
}}
