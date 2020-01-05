
import com.github.nekkan.jdakt.client.DiscordClient
import kotlinx.coroutines.ObsoleteCoroutinesApi
import net.dv8tion.jda.api.OnlineStatus.IDLE
import net.dv8tion.jda.api.entities.Activity.watching

@ObsoleteCoroutinesApi
class Main: DiscordClient("bot", { "." }) {

    override var application = settings {
        token = "opa."
        status = IDLE
        activity = watching("Something...")
    }
    //O token é usado no bot e depois é escondido caso você use 'settings {}' em application.

    override var onReady = {
        registerCommands(Command("uau"))
        registerEvents(Event())
    }()

}

@ObsoleteCoroutinesApi
fun main() {
    Main()
}
