package com.github.nekkan.jdakt.extensions.async

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Emote
import net.dv8tion.jda.api.entities.Message
import java.time.temporal.ChronoUnit

val Message.args get() = contentRaw.split(" ")

fun Message.react(clearReactions: Boolean = false, vararg reactions: String) {
    if(clearReactions) if(guild.selfMember.hasPermission(Permission.MANAGE_SERVER)) clearReactions().async()
    reactions.forEach { addReaction(it).async() }
}
fun Message.react(clearReactions: Boolean = false, vararg reactions: Emote) {
    if(clearReactions) if(guild.selfMember.hasPermission(Permission.MANAGE_SERVER)) clearReactions().async()
    reactions.forEach { addReaction(it).async() }
}
fun Message.refreshContent() = channel.retrieveMessageById(id).async()
fun Message.isEquals(content: String, ignoreCase: Boolean = false) = contentRaw.equals(content, ignoreCase
= true)

fun Message.timeDifference(anotherMessage: Message): Long = timeCreated.until(anotherMessage.timeCreated, ChronoUnit.MILLIS)
fun Message.edit(msg: String) = editMessage(msg).async()
fun Message.edit(msg: Message) = editMessage(msg).async()