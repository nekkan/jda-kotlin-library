package com.github.nekkan.jdakt.client

import com.github.nekkan.jdakt.command.DiscordCommand
import com.github.nekkan.jdakt.command.DiscordCommandManager
import com.github.nekkan.jdakt.event.DiscordEvent
import com.github.nekkan.jdakt.event.DiscordEventManager
import com.github.nekkan.jdakt.extensions.thread
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

    fun settings(info: DiscordSettings.() -> Unit): DiscordSettings {
        val i = DiscordSettings("", AccountType.BOT, 1, null, null).apply(info)
        val builder = JDABuilder(i.accountType).setToken(i.token)
        if(i.shards > 1) for(n in 0..i.shards) builder.useSharding(n, i.shards)
        i.activity?.let { builder.setActivity(it) }
        i.status?.let { builder.setStatus(it) }
        builder.setDisabledCacheFlags(EnumSet.of(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE))
        builder.setChunkingFilter(ChunkingFilter.NONE)
        jda = builder.build().awaitReady()
        i.token = "uau"
        events(); cmds()
        return i
    }

    abstract var application: DiscordSettings

    abstract var onReady: Unit

    fun addListeners(vararg listeners: ListenerAdapter) = discord.addEventListener(listeners)

    fun registerEvents(
        vararg events: DiscordEvent<*>
    ) = eventManager.add(*events)

    fun registerCommands(vararg commands: DiscordCommand) = commandManager.add(*commands)

    private fun events(context: CoroutineContext = thread()) {
        discord.addEventListener(object: ListenerAdapter() {
            override fun onGenericEvent(event: GenericEvent) {
                runBlocking(context) {
                    async(context) {
                        val evnt = eventManager.events.filter { it._class.simpleName!!.contentEquals(event::class.simpleName!!) }
                        if(evnt.firstOrNull() != null) {
                            val eventelement = evnt.first()
                            val run = eventelement.run(event, instance)
                            when (run) {
                                DiscordResult.ERROR -> eventelement._resultErrorEvent(event, instance)
                                DiscordResult.SUCCESS -> eventelement._resultSuccessEvent(event, instance)
                                DiscordResult.UNKNOWN -> eventelement._resultUnknownEvent(event, instance)
                                else -> eventelement._resultOtherEvent(event, instance)
                            }
                        }
                    }.await()
                }
            }
        })
    }

    private fun cmds(context: CoroutineContext? = null) {
        discord.addEventListener(object: ListenerAdapter() {
            override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {

                if(event.author.isBot) return

                val prefix = instance.prefix(event.guild)
                val content = event.message.contentRaw.toLowerCase().split(" ").toMutableList()
                if(!content[0].startsWith(prefix)) return@onGuildMessageReceived

                val commandName = {
                   val x = content[0]
                   content.remove(x)
                   x.substring(prefix.length)
                }()

                var command: DiscordCommand? = commandManager.commands[commandName]
                if(command == null) command = commandManager.commands[commandManager.aliases[commandName]]

                command?.let {
                    val thread = context ?: newSingleThreadContext("command-"+commandName+"-"+Random.nextInt(99999))
                    runBlocking(thread) {
                        async(thread) {
                            val run = it.run(instance, content, event)
                            when(run) {
                                DiscordResult.ERROR -> it.commandResultErrorEvent(instance, content, event)
                                DiscordResult.SUCCESS -> it.commandResultSuccessEvent(instance, content, event)
                                DiscordResult.UNKNOWN -> it.commandResultUnknownEvent(instance, content, event)
                                else -> it.commandResultOtherEvent(instance, content, event)
                            }
                        }.await()
                    }
                }

            }
        })

    }

    init {
        clients = clients.toMutableMap().apply { put(name, instance) }.toMap()
    }

}
