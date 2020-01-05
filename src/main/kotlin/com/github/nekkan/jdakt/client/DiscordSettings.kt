package com.github.nekkan.jdakt.client

import net.dv8tion.jda.api.AccountType
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity

data class DiscordSettings(
    var token: String,
    var accountType: AccountType = AccountType.BOT,
    var shards: Int = 0,
    var activity: Activity? = null,
    var status: OnlineStatus? = null
)