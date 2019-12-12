package com.github.nekkan.jdakt.extensions.async

import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.requests.RestAction
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <T> RestAction<T>.a(): T {
    return suspendCoroutine { c ->
        this.queue({ c.resume(it)}, { c.resumeWithException(it) })
    }
}

fun <T> RestAction<T>.async(): T = runBlocking { a() }

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
fun MessageChannel.message(msg: MessageEmbed, func: (Message) -> Unit) = sendMessage(msg).queue() {
        m -> func(m) }
fun MessageChannel.msg(msg: MessageBuilder) = sendMessage(msg.build()).async()
fun MessageChannel.message(msg: MessageBuilder) = sendMessage(msg.build()).queue()
fun MessageChannel.msg(msg: MessageEmbed) = sendMessage(msg).async()
fun MessageChannel.message(msg: MessageEmbed) = sendMessage(msg).queue()
fun MessageChannel.message(msg: MessageBuilder, func: (Message) -> Unit) = sendMessage(msg.build()).queue() {
        m -> func(m) }


fun MessageChannel.retrieve(amountOfMessages: Int): ArrayList<Message> {
    val messages = ArrayList<Message>(amountOfMessages)
    iterableHistory.cache(false).forEachAsync { msg ->
        messages.add(msg)
        return@forEachAsync messages.size < amountOfMessages
    }
    return messages
}


