package com.github.nekkan.jdakt.dsl.command

import com.github.nekkan.jdakt.extensions.async.args
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class JKTCommand(val jda: JDA, val prefix: String, val name: String, val aliases: List<String>? = null, 
                      val block: (GuildMessageReceivedEvent) -> Unit) : ListenerAdapter() {

    fun execute() {


        jda.addEventListener(object: ListenerAdapter() {
            override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
                if(event.author.isBot) return
                val args = event.message.args
                var isRight = false
                aliases?.forEach {
                    if(args[0].equals("$prefix$it", true)) {
                        isRight = true
                        return@forEach
                    }
                }
                if(args[0].equals("${prefix}${name}", true)) isRight = true
                if(isRight) block(event)
            }
        })
    }

}

open class JKTCommandCreator(
    var prefix: Any = "",
    var name: String,
    var aliases: List<String>? = null,
    var description: String? = null,
    var usage: String? = null,
    var extra: Any? = null
) { init { if(usage == "") { usage = "$prefix$name" } } }

fun creator(creator: JKTCommandCreator.() -> Unit) = JKTCommandCreator(
    "","",null,null,null, null).apply(creator)

fun command(jda: JDA, prefix: String, name: String, aliases: List<String> = listOf(),
            block: (GuildMessageReceivedEvent) -> Unit) = JKTCommand(jda, prefix, name, aliases, block).execute()

fun command(jda: JDA, prefix: Char, name: String, aliases: List<String> = listOf(),
            block: (GuildMessageReceivedEvent) -> Unit) = JKTCommand(jda, prefix.toString(), name, aliases, block).execute()

fun command(jda: JDA, commandCreator: JKTCommandCreator, block: (GuildMessageReceivedEvent) -> Unit) =
    JKTCommand(jda, commandCreator.prefix.toString(), commandCreator.name, commandCreator.aliases, block).execute()
