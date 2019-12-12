package com.github.nekkan.jdakt.dsl.module

import com.github.nekkan.jdakt.dsl.module.categories.command.CommandBuilder
import com.github.nekkan.jdakt.dsl.module.categories.command.ModuleCommand
import com.github.nekkan.jdakt.dsl.module.categories.event.ModuleEvent
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import kotlin.reflect.KClass

class ModuleBuilder(val module: Module) {

    fun command(
        commandBuilder: CommandBuilder,
        command: (GuildMessageReceivedEvent) -> Unit
    ) = ModuleCommand(module, commandBuilder, command)

    inline fun <reified T: Event> event(
        noinline run: (T) -> Unit
    ): ModuleEvent {
        return ModuleEvent(module, T::class as KClass<Event>, run as (Event) -> Unit)
    }

}
