
import com.github.nekkan.jdakotlinlibrary.client.DiscordClient
import com.github.nekkan.jdakotlinlibrary.client.DiscordResult
import com.github.nekkan.jdakotlinlibrary.collections.utils.argument
import com.github.nekkan.jdakotlinlibrary.collections.utils.fail
import com.github.nekkan.jdakotlinlibrary.command.DiscordCommand
import com.github.nekkan.jdakotlinlibrary.extensions.map
import com.github.nekkan.jdakotlinlibrary.extensions.reply
import kotlinx.coroutines.ObsoleteCoroutinesApi
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import kotlin.random.Random

@ObsoleteCoroutinesApi
class Command: DiscordCommand() {

    override val settings = settings {
        name = "roll"
    }

    override fun run(client: DiscordClient, arguments: List<String>, event: GuildMessageReceivedEvent) = command(event) {
        val stringNum = argument(arguments, 1) ?: return@command fail { reply("Você precisa citar um argumento.") }
        val number = (stringNum as String).toIntOrNull() ?: return@command fail { reply("Você precisa citar um número *inteiro* válido.") }
        map { number }.map { Random.nextInt(number) }.subscribe(async = true) {
            reply("» O número foi escolhido foi **${number}**.")
        }
        DiscordResult.SUCCESS
    }

}