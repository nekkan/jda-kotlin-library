package com.github.nekkan.jdakotlinlibrary.command

data class DiscordCommandSettings(
    var name: String,
    var aliases: List<String>? = null,
    var description: String? = null,
    var usage: String? = null
)