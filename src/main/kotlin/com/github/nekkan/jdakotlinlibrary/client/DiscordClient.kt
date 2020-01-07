package com.github.nekkan.jdakotlinlibrary.client

import com.github.nekkan.jdakotlinlibrary.command.DiscordCommand
import com.github.nekkan.jdakotlinlibrary.command.DiscordCommandManager
import com.github.nekkan.jdakotlinlibrary.event.DiscordEvent
import com.github.nekkan.jdakotlinlibrary.event.DiscordEventManager
import com.github.nekkan.jdakotlinlibrary.extensions.map
import com.github.nekkan.jdakotlinlibrary.extensions.thread
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.AccountType
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.utils.ChunkingFilter
import net.dv8tion.jda.api.utils.cache.CacheFlag
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

@ObsoleteCoroutinesApi
abstract class DiscordClient(var name: String, var prefix: (Guild) -> String) {

    private lateinit var jda: JDA

    val discord get() = jda

    val instance get() = this

    val commandManager = DiscordCommandManager(this)
    val eventManager = DiscordEventManager(this)

    //Observação: A classe o nome mencionado precisa ser instanciada pelo menos uma vez.
    companion object {

        private var clients = mapOf<String, DiscordClient>()
            private set

        fun byName(name: String) = clients[name] }

    fun settings(context: CoroutineContext? = null, builder: JDABuilder = JDABuilder(AccountType.BOT),
                 info: DiscordSettings.() -> Unit): DiscordSettings {
        val i = DiscordSettings("", 1, null, null).apply(info)
        builder.setToken(i.token)
        if (i.shards > 1) for (n in 0..i.shards) builder.useSharding(n, i.shards)
        i.activity?.let { builder.setActivity(it) }
        i.status?.let { builder.setStatus(it) }
        builder.setDisabledCacheFlags(EnumSet.of(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE))
        builder.setChunkingFilter(ChunkingFilter.NONE)
        jda = builder.build().awaitReady()
        i.token = "uau"
        events(context); cmds(context)
        return i
    }


    abstract var application: DiscordSettings

    abstract var onReady: Unit

    fun addListeners(vararg listeners: ListenerAdapter) = discord.addEventListener(listeners)

    fun registerEvents(
        vararg events: DiscordEvent
    ) = eventManager.add(*events)

    fun registerCommands(vararg commands: DiscordCommand) = commandManager.add(*commands)

    private fun events(context: CoroutineContext? = null) {
        discord.addEventListener(object: ListenerAdapter() {
            override fun onGenericEvent(event: GenericEvent) {
                val t = context ?: thread()
                runBlocking(t) {
                    async(t) {
                        val name = event::class.qualifiedName
                        val evnt = eventManager.events.filter { it.events[name] != null }
                        if(evnt.firstOrNull() != null) {
                            val eventelement = evnt.first()
                            eventelement.events[name]!!(event, instance)
                        }
                    }.await()
                }
            }
        })
    }

    private fun cmds(context: CoroutineContext? = null) {
        discord.addEventListener(object: ListenerAdapter() {
            override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {

                val prefix = instance.prefix(event.guild)
                val content = event.message.contentRaw.toLowerCase().split(" ").toMutableList()
                map { event }.filter { !it.author.isBot }?.filter { content[0].startsWith(prefix) }?.subscribe {

                    val commandName = {
                        val x = content[0]
                        content.remove(x)
                        x.removePrefix(prefix)
                    }()

                    val s = commandManager.commands[commandName] ?: commandManager.commands[commandManager.aliases.get(commandName)]

                    s?.let {
                        val thread = context ?: newSingleThreadContext("command-" + commandName + "-" + Random.nextInt(99999))
                        runBlocking(thread) {
                            async(thread) {
                                val run = it.run(instance, content, event)
                                when (run) {
                                    DiscordResult.ERROR -> it.commandResultErrorEvent(instance, content, event)
                                    DiscordResult.SUCCESS -> it.commandResultSuccessEvent(instance, content, event)
                                    DiscordResult.UNKNOWN -> it.commandResultUnknownEvent(instance, content, event)
                                    else -> it.commandResultOtherEvent(instance, content, event)
                                }
                            }.await()
                        }
                    }
                }

            }
        })

    }

    init {
        clients = clients.toMutableMap().apply { put(name, instance) }.toMap()
    }

}
