package com.github.nekkan.jdakt.collections.embed

import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color

class DiscordEmbed(
    var title: Pair<String, String?>?,
    var author: Triple<String, String?, String?>?,
    var description: String?,
    var color: Color?,
    var footer: Pair<String, String?>?,
    var fields: List<DiscordEmbedField>?
) {

    fun builder(): EmbedBuilder {
        val embed = EmbedBuilder()
        val t = this
        embed.apply {
            title?.let { setTitle(it.first, it.second) }
            author?.let { setAuthor(it.first, it.second, it.third) }
            setDescription(description)
            setColor(color)
            footer?.let { setFooter(it.first, it.second) }
            t.fields?.forEach {
                if(it.text == "") addBlankField(it.inline)
                else addField(it.title, it.text, it.inline)
            }
        }
        return embed
    }

}

fun embed(embed: DiscordEmbed.() -> Unit) = (DiscordEmbed(
    null, null, null, null, null, null
).apply(embed)).builder()