@file:JvmName("ChannelExtensions")
package com.github.nekkan.jdakt.extensions

import com.github.nekkan.jdakt.extensions.async.async
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.VoiceChannel

var TextChannel.name: String
get() = this.name
set(newName) = manager.setName(newName).queue()

var TextChannel.topic: String?
get() = this.topic
set(newTopic) = manager.setTopic(newTopic).queue()

var VoiceChannel.name: String
get() = this.name
set(newName) = this.manager.setName(newName).queue()

fun TextChannel.createCopy(newName: String, newTopic: String? = null) {
    val copy = createCopy()
    copy.setName(newName)
    if(topic != null) copy.setTopic(newTopic)
    copy.async()
}

fun VoiceChannel.createCopy(newName: String) {
    val copy = createCopy()
    copy.setName(newName)
    copy.async()
}
