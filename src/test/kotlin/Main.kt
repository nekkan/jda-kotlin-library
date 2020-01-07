
import com.github.nekkan.jdakotlinlibrary.client.DiscordClient
import com.github.nekkan.jdakotlinlibrary.extensions.tag
import kotlinx.coroutines.ObsoleteCoroutinesApi
import net.dv8tion.jda.api.OnlineStatus.IDLE
import net.dv8tion.jda.api.entities.Activity.watching

@ObsoleteCoroutinesApi
class Main: DiscordClient("bot", { "." }) {


    override var application = settings() {
        token = "App's Token"
        status = IDLE
        activity = watching("Something...")
    }

    override var onReady = {
        registerCommands(Command())
        registerEvents(Event())
        println("» Logado como ${discord.tag}")
    }()

}

@ObsoleteCoroutinesApi
fun main() {
    Main()
}
