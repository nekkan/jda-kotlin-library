package com.github.nekkan.jdakt.dsl.module

import com.github.nekkan.jdakt.dsl.module.categories.command.CommandBuilder
import com.github.nekkan.jdakt.extensions.async.args
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import kotlin.reflect.KClass

open class Module {

    val commands = mutableMapOf<CommandBuilder, (GuildMessageReceivedEvent) -> Unit>()
    val events = mutableMapOf<KClass<Event>, (Event) -> Unit>()
    lateinit var info: ModuleBuilder.() -> Unit

    open fun module(module: ModuleBuilder.() -> Unit): ModuleBuilder = builder {}

    fun builder(creator: ModuleBuilder.() -> Unit): ModuleBuilder {
        info = creator
        return ModuleBuilder(this).apply(info)
    }

}

fun JDABuilder.addModule(module: Module) {
    module.module { module.info(this) }
    module.commands.forEach { (builder, command) ->
        addEventListeners(object: ListenerAdapter() {
            override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
                if(!builder.botsCanUse && event.author.isBot) return
                var arguments = event.message.args
                var isCorrect = false
                if(arguments[0].equals("${builder.prefix(event.guild)}${builder.name}", true)) isCorrect = true
                if(!isCorrect) {
                    val usedAliases = builder.aliases?.filter { arguments[0].equals("${builder.prefix(event.guild)}$it", true) }
                    usedAliases?.let {
                        if(it.isNotEmpty()) isCorrect = true
                    }
                }
                if(isCorrect) command(event)
            }
        })
    }
    module.events.forEach { (type, run) ->
        addEventListeners(object: ListenerAdapter() {
            override fun onGenericEvent(event: GenericEvent) {
                println("${type.simpleName} ${event::class.simpleName}")
                if(type::class.simpleName!! == event::class.simpleName!!) run(event as Event)
            }
        })
    }
}

fun JDABuilder.addModules(vararg modules: Module) = modules.forEach { addModule(it) }