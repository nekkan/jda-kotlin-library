package com.github.nekkan.jdakt.extensions

import com.github.nekkan.jdakt.extensions.async.async
import com.github.nekkan.jdakt.extensions.async.msg
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.Region
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.VoiceChannel

fun Guild.msg(channel: MessageChannel, builder: MessageBuilder) = channel.msg(builder)

var Guild.name: String
get() = this.name
set(newName) = this.manager.setName(newName).queue()

var Guild.region: Region
get() = this.region
set(newRegion) = this.manager.setRegion(newRegion).queue()

fun Guild.nullableTextChannelById(id: String?): TextChannel? {
    if(id == null) return null
    val ch = getTextChannelById(id) ?: return null
    return ch
}

fun Guild.nullableVoiceChannelById(id: String?): VoiceChannel? {
    if(id == null) return null
    val ch = getVoiceChannelById(id) ?: return null
    return ch
}

fun Guild.nullableTextChannelById(id: Long?): TextChannel? {
    if(id == null) return null
    val ch = getTextChannelById(id) ?: return null
    return ch
}

fun Guild.nullableVoiceChannelById(id: Long?): VoiceChannel? {
    if(id == null) return null
    val ch = getVoiceChannelById(id) ?: return null
    return ch
}