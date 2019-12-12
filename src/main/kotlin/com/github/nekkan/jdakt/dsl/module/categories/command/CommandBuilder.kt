package com.github.nekkan.jdakt.dsl.module.categories.command

import net.dv8tion.jda.api.entities.Guild

class CommandBuilder(
    var prefix: (Guild) -> String = { it -> "" },
    var name: String,
    var aliases: List<String>?,
    var description: String?,
    var usage: String?,
    var botsCanUse: Boolean = false,
    var extra: Any?
)

fun commandBuilder(builder: CommandBuilder.() -> Unit) = CommandBuilder({ "" }, "",null,null,
    null,false, null).apply(builder)