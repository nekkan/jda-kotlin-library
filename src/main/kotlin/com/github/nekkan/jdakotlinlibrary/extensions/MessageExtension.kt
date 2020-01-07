@file:JvmName("MessageExtension")
package com.github.nekkan.jdakotlinlibrary.extensions

import net.dv8tion.jda.api.entities.Message

var dataTags = mapOf<Message, List<String>>()
    private set

val Message.dataTags: List<String> get() {
    val tags = com.github.nekkan.jdakotlinlibrary.extensions.dataTags[this]
    tags?.let { return it }
    com.github.nekkan.jdakotlinlibrary.extensions.dataTags = com.github.nekkan.jdakotlinlibrary.extensions.dataTags.toMutableMap().apply {
        set(this@dataTags, listOf<String>())
    }.toMap()
    return listOf<String>()
}

fun Message.addDataTags(vararg tags: String) {
    val list = dataTags
    com.github.nekkan.jdakotlinlibrary.extensions.dataTags = com.github.nekkan.jdakotlinlibrary.extensions.dataTags.toMutableMap().apply {
        set(this@addDataTags, list.toMutableList().apply { addAll(tags) }.toList())
    }.toMap()
}

fun Message.removeDataTags(vararg tags: String) {
    val list = dataTags
    com.github.nekkan.jdakotlinlibrary.extensions.dataTags = com.github.nekkan.jdakotlinlibrary.extensions.dataTags.toMutableMap().apply {
        set(this@removeDataTags, list.toMutableList().apply { removeAll(tags) }.toList())
    }.toMap()
}

fun Message.args() = contentRaw.split(" ")