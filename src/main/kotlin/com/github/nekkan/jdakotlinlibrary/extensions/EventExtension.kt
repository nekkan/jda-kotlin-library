@file:JvmName("EventExtension")
package com.github.nekkan.jdakotlinlibrary.extensions

import com.github.nekkan.jdakotlinlibrary.collections.utils.AnyMap
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.GenericMessageEvent
import net.dv8tion.jda.api.events.message.guild.GenericGuildMessageEvent

infix fun GenericGuildMessageEvent.reply(message: Message) = channel.reply(message)
infix fun GenericGuildMessageEvent.reply(message: CharSequence) = channel.reply(message)
infix fun GenericGuildMessageEvent.reply(message: MessageEmbed) = channel.reply(message)
infix fun GenericGuildMessageEvent.reply(message: EmbedBuilder) = channel.reply(message.build())
infix fun <T> GenericGuildMessageEvent.map(s: (GenericGuildMessageEvent) -> T) = AnyMap<T>(s(this))
infix fun GenericMessageEvent.reply(message: Message) = channel.reply(message)
infix fun GenericMessageEvent.reply(message: CharSequence) = channel.reply(message)
infix fun GenericMessageEvent.reply(message: MessageEmbed) = channel.reply(message)
infix fun GenericMessageEvent.reply(message: EmbedBuilder) = channel.reply(message.build())
infix fun <T> GenericMessageEvent.map(s: (GenericMessageEvent) -> T) = AnyMap<T>(s(this))