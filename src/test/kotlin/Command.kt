
import com.github.nekkan.jdakt.client.DiscordClient
import com.github.nekkan.jdakt.client.DiscordResult
import com.github.nekkan.jdakt.collections.embed.embed
import com.github.nekkan.jdakt.collections.embed.field
import com.github.nekkan.jdakt.command.DiscordCommand
import com.github.nekkan.jdakt.extensions.reply
import kotlinx.coroutines.ObsoleteCoroutinesApi
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import java.awt.Color

@ObsoleteCoroutinesApi
class Command(var uou: String): DiscordCommand() {

    override val settings = settings {
        name = uou
        aliases = listOf("cmdalias")
    }

    override fun run(client: DiscordClient, arguments: List<String>, event: GuildMessageReceivedEvent) = command(event) {
        reply(embed {
            title = "Oi!" to null
            color = Color.CYAN
            description = "Uou!"
            fields = listOf(
                field(inline = true) {
                    "Olá" to "mundo!" //Título | Valor
                }
            )
        })
        DiscordResult.SUCCESS
    }

}