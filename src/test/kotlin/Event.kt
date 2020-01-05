
import com.github.nekkan.jdakt.client.DiscordResult
import com.github.nekkan.jdakt.event.DiscordEvent
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent

class Event: DiscordEvent<GuildMessageReactionAddEvent>(GuildMessageReactionAddEvent::class) {

    init {

        event { event, client ->
            println("Uma reação foi adicionada a mensagem com o ID ${event.messageId}")
            DiscordResult.SUCCESS
        }

    }

}