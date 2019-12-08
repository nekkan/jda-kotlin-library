package com.github.nekkan.jdakt.dsl.embed

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import java.awt.Color

class JKTEmbedCreator(
    var content: String? = null, //Embed content
    var color: Color? = null, //Embed color
    var title: String? = null, //Embed title
    var author: String? = null, //Embed author
    var authorUrl: String? = null, //URL of author
    var authorIconUrl: String? = null, //Icon of author
    var description: String? = null, //Embed description
    var fields: Map<String?, Boolean>? = null, //Embed fields ("title||value", inline)
    var footer: String? = null, //Embed footer
    var footerIconUrl: String? = null //Embed footer icon url
) {

    val get: MessageBuilder
    get() {
        val embed = EmbedBuilder()
        if( color != null) embed.setColor(color)
        if (title != null) embed.setTitle(title)
        if (author != null) embed.setAuthor(author, authorUrl, authorIconUrl)
        if (description != null) embed.setDescription(description)
        fields?.forEach {
            if(it.key == null) embed.addBlankField(it.value)
            else embed.addField(it.key?.split("||")?.get(0), it.key?.split("||")?.get(1), it.value)
        }
        if (footer != null) {
            if(footerIconUrl != null) embed.setFooter(footer, footerIconUrl)
            else embed.setFooter(footer)
        }
        if (content != null) return MessageBuilder().append(content).setEmbed(embed.build())
        return MessageBuilder().setEmbed(embed.build())
    }

}

fun embed(
    block: JKTEmbedCreator.() -> Unit): MessageBuilder = JKTEmbedCreator(
    null, null, null, null, null, null,
    null, null, null).apply(block).get