@file:JvmName("DiscordExtension")
package com.github.nekkan.jdakotlinlibrary.extensions

import net.dv8tion.jda.api.JDA

val JDA.self get() = selfUser
val JDA.tag get() = self.asTag