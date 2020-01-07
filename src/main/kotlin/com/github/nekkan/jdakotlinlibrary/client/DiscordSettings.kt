package com.github.nekkan.jdakotlinlibrary.client

import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity

data class DiscordSettings(
    var token: String,
    var shards: Int = 0,
    var activity: Activity? = null,
    var status: OnlineStatus? = null
)