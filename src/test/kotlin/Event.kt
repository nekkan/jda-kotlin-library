
import com.github.nekkan.jdakotlinlibrary.client.DiscordResult
import com.github.nekkan.jdakotlinlibrary.event.DiscordEvent
import com.github.nekkan.jdakotlinlibrary.extensions.reply
import kotlinx.coroutines.ObsoleteCoroutinesApi
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent

@ObsoleteCoroutinesApi
class Event: DiscordEvent() {

    init {

        event<GuildMessageReactionAddEvent> { event, client ->
            event.reply("Reação feita.")
            DiscordResult.SUCCESS
        }

    }

}