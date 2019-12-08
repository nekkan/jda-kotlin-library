package com.github.nekkan.jdakt.dsl.event

import com.github.nekkan.jdakt.utils.jda.register
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.UpdateEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class JKTGenericEvent() {

    inline fun <reified T: Event> register(jda: JDA, crossinline block: (T) -> Unit) {
        jda.register(
            object : ListenerAdapter() {
                override fun onGenericEvent(event: GenericEvent) {
                    if(event !is T) return
                    block(event)
                }

            }
        )
    }

    inline fun <reified T: UpdateEvent<*, *>> registerUpdate(jda: JDA, crossinline block: (T) -> Unit) {
        jda.register(
            object : ListenerAdapter() {
                override fun onGenericEvent(event: GenericEvent) {
                    if(event !is T) return
                    block(event)
                }

            }
        )
    }

}

inline fun <reified T: Event> JDA.on(crossinline block: (T) -> Unit) = JKTGenericEvent().
        register<T>(this, block)

inline fun <reified T: UpdateEvent<*, *>> JDA.onUpdate(crossinline block: (T) -> Unit) = JKTGenericEvent().
    registerUpdate<T>(this, block)