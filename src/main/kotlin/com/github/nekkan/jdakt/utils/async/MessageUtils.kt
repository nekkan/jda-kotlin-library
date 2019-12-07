package com.github.nekkan.jdakt.utils.async

import com.github.nekkan.jdakt.utils.jda.pause
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.*
import net.dv8tion.jda.api.requests.RestAction
import java.time.temporal.ChronoUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <T> RestAction<T>.a(): T {
    return suspendCoroutine { c ->
        this.queue({ c.resume(it)}, { c.resumeWithException(it) })
    }
}

fun <T> RestAction<T>.async(): T = runBlocking { a() }

val Message.args get() = contentRaw.split(" ")
fun MessageChannel.msg(msg: String) = sendMessage(msg).async()
fun MessageChannel.msg(msg: Message) = sendMessage(msg).async()
fun MessageChannel.msg(msg: EmbedBuilder) = sendMessage(msg.build()).async()
fun MessageChannel.message(msg: String) = sendMessage(msg).queue()
fun MessageChannel.message(msg: Message) = sendMessage(msg).queue()
fun MessageChannel.message(msg: EmbedBuilder) = sendMessage(msg.build()).queue()
fun MessageChannel.message(msg: String, func: (Message) -> Unit) = sendMessage(msg).queue() {
    m -> func(m) }
fun MessageChannel.message(msg: Message, func: (Message) -> Unit) = sendMessage(msg).queue() {
    m -> func(m) }
fun MessageChannel.message(msg: EmbedBuilder, func: (Message) -> Unit) = sendMessage(msg.build()).queue() {
    m -> func(m) }
fun MessageChannel.msg(msg: MessageBuilder) = sendMessage(msg.build()).async()
fun MessageChannel.message(msg: MessageBuilder) = sendMessage(msg.build()).queue()
fun MessageChannel.message(msg: MessageBuilder, func: (Message) -> Unit) = sendMessage(msg.build()).queue() {
        m -> func(m) }
fun Message.edit(msg: String) = editMessage(msg).async()
fun Message.edit(msg: Message) = editMessage(msg).async()

fun Message.react(clearReactions: Boolean = false, vararg reactions: String) {
    if(clearReactions) clearReactions().async()
    reactions.forEach { addReaction(it).async() }
}
fun Message.react(clearReactions: Boolean = false, vararg reactions: Emote) {
    if(clearReactions) if(guild.selfMember.hasPermission(Permission.MANAGE_SERVER)) clearReactions().async()
    reactions.forEach { addReaction(it).async() }
}
fun Message.refreshContent() = channel.retrieveMessageById(id).async()
fun Message.isEquals(content: String, ignoreCase: Boolean = false) = contentRaw.equals(content, ignoreCase
    = true)

fun Message.deleteAfter(millis: Long) {
    pause(millis)
    delete().async()
}

fun Message.timeDifference(anotherMessage: Message): Long = timeCreated.until(anotherMessage.timeCreated, ChronoUnit.MILLIS)

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




