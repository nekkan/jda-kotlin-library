package com.github.nekkan.jdakt.dsl.module.categories.event

import com.github.nekkan.jdakt.dsl.module.Module
import net.dv8tion.jda.api.events.Event
import kotlin.reflect.KClass

class ModuleEvent(private val module: Module, val type: KClass<Event>, run: (Event) -> Unit) {

    init {
        module.events[type] = run
    }

}