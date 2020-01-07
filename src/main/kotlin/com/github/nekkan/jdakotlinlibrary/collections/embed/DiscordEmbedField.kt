package com.github.nekkan.jdakotlinlibrary.collections.embed

data class DiscordEmbedField(var title: String, var text: String = "", var inline: Boolean = false)

fun field(inline: Boolean, content: () -> (Pair<String, String>)) = DiscordEmbedField(content().first, content().second, inline)