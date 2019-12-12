package com.github.nekkan.jdakt.utils.jda

import com.github.nekkan.jdakt.dsl.module.Module
import com.github.nekkan.jdakt.dsl.module.addModule
import net.dv8tion.jda.api.AccountType
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.hooks.IEventManager
import net.dv8tion.jda.api.hooks.ListenerAdapter

fun buildBot(token: String, builder: JDABuilder.() -> Unit): JDA {
    return (JDABuilder(AccountType.BOT).setToken(token)).apply(builder).build().awaitReady()
}

fun JDABuilder.listeners(listenerAdapters: () -> ListenerAdapter) = addEventListeners(listenerAdapters())

fun JDABuilder.activity(activity: () -> Activity) = setActivity(activity())

fun JDABuilder.onlineStatus(onlineStatus: () -> OnlineStatus) = setStatus(onlineStatus())

fun JDABuilder.eventManager(eventManager: () -> IEventManager) = setEventManager(eventManager())

fun JDABuilder.modules(modules: () -> List<Module>) = modules().forEach { addModule(it) }