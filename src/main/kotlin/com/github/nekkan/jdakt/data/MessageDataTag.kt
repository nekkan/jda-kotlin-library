@file:JvmName("MessageDataTag")
package com.github.nekkan.jdakt.data

import net.dv8tion.jda.api.entities.Message

private val tags = mutableMapOf<Message, MutableList<String>>()

fun Message.addDataTag(dataTag: String) {
    if(tags[this] == null) tags[this] = mutableListOf<String>(dataTag)
    else {
        val list = tags[this]
        list!!.add(dataTag)
        tags[this] = list
    }
}

fun Message.setDataTags(dataTagsList: List<String>) = tags.put(this, dataTagsList.toMutableList())

val Message.dataTags: List<String>? get() = tags[this]?.toList()

fun Message.containsDataTag(dataTag: String, ignoreCase: Boolean = false): Boolean {
    val t = tags[this]
    t?.let{
        val filter: String? = t.first { it.equals(dataTag, ignoreCase) }
        filter?.let { return true }
    }
    return false
}