package com.github.nekkan.jdakt.utils.jda

import net.dv8tion.jda.api.AccountType
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.hooks.ListenerAdapter

fun JDA.register(listener: ListenerAdapter) = addEventListener(listener)

fun build(token: String, awaitReady: Boolean = false, accountType: AccountType = AccountType.BOT): JDA {
    if(!awaitReady) return JDABuilder(accountType).setToken(token).build()
    return JDABuilder(accountType).setToken(token).build().awaitReady()
}