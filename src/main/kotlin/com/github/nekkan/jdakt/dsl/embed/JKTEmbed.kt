package com.github.nekkan.jdakt.dsl.embed

import com.github.nekkan.jdakt.extensions.async.msg
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageChannel
import java.awt.Color
import java.time.Instant
import java.time.temporal.TemporalAccessor

fun embed(embedBuilder: EmbedBuilder.() -> Unit) = EmbedBuilder().apply(embedBuilder)

fun EmbedBuilder.title(text: () -> String) = setTitle(text())

fun EmbedBuilder.timestamp(temporalAccessor: () -> TemporalAccessor = { Instant.now() }) =
    setTimestamp(temporalAccessor())

fun EmbedBuilder.description(description: () -> String = { ""; "" }) {
    description().forEach { appendDescription("$it") }
}

fun EmbedBuilder.descriptionAppend(text: () -> String = { "" }) = appendDescription("${text()}\n")

fun EmbedBuilder.color(color: () -> Color) = setColor(color())

fun EmbedBuilder.author(name: String, url: String? = null, icon: String? = null) = setAuthor(name, url, icon)

fun EmbedBuilder.thumbnail(url: () -> String) = setThumbnail(url())

fun EmbedBuilder.image(url: () -> String) = setImage(url())

fun EmbedBuilder.footer(url: String? = null, text: () -> String) = setFooter(text(), url)

fun EmbedBuilder.entry(inline: Boolean = false, title: String, description: () -> String) {
    if(description() == "" && title == ""){
        addBlankField(inline)
    } else addField(title, description(), inline)
}

fun EmbedBuilder.send(channel: MessageChannel) = channel.msg(this.build())

