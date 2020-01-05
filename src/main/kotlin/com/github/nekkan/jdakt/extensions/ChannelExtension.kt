@file:JvmName("ChannelExtension")
package com.github.nekkan.jdakt.extensions

import com.github.nekkan.jdakt.collections.embed.DiscordEmbed
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.requests.RestAction
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <T> RestAction<T>.async(): T {
    return suspendCoroutine { c ->
        this.queue({ c.resume(it)}, { c.resumeWithException(it) })
    }
}

infix fun MessageChannel.reply(message: Message) = runBlocking(thread()) { sendMessage(message).async() }
infix fun MessageChannel.reply(message: CharSequence) = runBlocking(thread()) { sendMessage(message).async() }
infix fun MessageChannel.reply(message: MessageEmbed) = runBlocking(thread()) { sendMessage(message).async() }
infix fun MessageChannel.embed(embed: EmbedBuilder) = reply(embed.build())
infix fun MessageChannel.embed(embed: DiscordEmbed) = reply(embed.builder().build())
infix fun MessageChannel.embed(embed: MessageEmbed) = reply(embed)