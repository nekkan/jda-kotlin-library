package com.github.nekkan.jdakt

import com.github.nekkan.jdakt.utils.jda.build
import net.dv8tion.jda.api.JDA


object Main {

    @JvmStatic lateinit var jda: JDA

    @JvmStatic
    fun main(args: Array<String>) {
        jda = build(
            "NjI4MzczNzkwMDgyMDcyNTg1.Xem-bQ.LkqqROQUBpIbTOhuZbNHmc2fao4",
            true
        )
        for(a in 0..20) println(" ")
        println("Bot ligado.")
        Testy()
    }



}