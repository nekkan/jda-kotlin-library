package com.github.nekkan.jdakt.dsl.module.categories.command

import com.github.nekkan.jdakt.dsl.module.Module
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class ModuleCommand(
    module: Module,
    commandBuilder: CommandBuilder,
    command: (GuildMessageReceivedEvent) -> Unit
) {

    init {
        module.commands[commandBuilder] = command
    }

}