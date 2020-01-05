@file:JvmName("EventExtension")
package com.github.nekkan.jdakt.extensions

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

infix fun GuildMessageReceivedEvent.reply(message: Message) = channel.reply(message)
infix fun GuildMessageReceivedEvent.reply(message: CharSequence) = channel.reply(message)
infix fun GuildMessageReceivedEvent.reply(message: MessageEmbed) = channel.reply(message)
infix fun GuildMessageReceivedEvent.reply(message: EmbedBuilder) = channel.reply(message.build())