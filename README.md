[version]: https://raster.shields.io/badge/Versão--2.0.0-yellow.svg
![version] [![](https://jitpack.io/v/nekkan/JDA-kt.svg)](https://jitpack.io/#nekkan/JDA-kt) 

# JDA-kt
A função principal do JDA-kt é lhe auxiliar na criação de eventos, comandos, embeds, entre outras coisas para facilitar seu código e deixá-lo com uma ótima legibilidade com o JDA.

# Instalação
Você pode obter o JDA-kt em Gradle, Maven, SBT e em Leiningen no Jitpack! [Clique aqui!](https://jitpack.io/#nekkan/JDA-kt)

Você precisa das dependências: 
- **[JDA](https://github.com/DV8FromTheWorld/JDA)** (obrigatório);
- **[Kotlin Coroutines](https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-android)** (obrigatório).
- **[Kotlin Reflect](https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-reflect)**

#Por que utilizar?
O JDA-kt faz todos os eventos e comandos em **async**, além de adicionar facilidade e legibilidade em seus códigos.

#Criando um client
Crie uma classe e implemente **DiscordClient()** na mesma. Os parâmetros são **nome, prefix de comandos**. Em seguida, dê *override* no método **application** informando assim o token e algumas informações opcionais como **activity e status**. Em seguida, você deve dar override em **onReady** e inserir coisas de quando iniciar. Exemplo:
```kotlin
@ObsoleteCoroutinesApi
class HelloDiscord: DiscordClient("discordbot1", ".") {

    override var application = settings {
        token = "MVDSAOKkvb94958cvbSX.XhI6eg.NOTAREALTOKEN.GFDGF4543VFDKC_sdfkkj"
        status = IDLE
        activity = watching("Something...")
    }
   
    override var onReady = {
        println("Logado como ${discord.user.name}#${discord.user.discriminator}")
    }()

}
```

#Criando eventos
Para criar um evento, você deve criar uma classe na qual implementa **DiscordEvent()**, informando como *generic* o evento desejado e como parâmetro a classe do evento desejado. Em seguida você deve usar o **init** do Kotlin e usar o método **event()** e dentro do mesmo inserir suas informações. Em seguida você deve retornar um valor do ENUM **DiscordResult**. Exemplo:
```kotlin
class HelloEvent: DiscordEvent<GuildMessageReceivedEvent>(GuildMessageReceivedEvent::class) {

    init {

        fun event { event, client ->
            println("Uma reação foi adicionada a mensagem com o ID ${event.messageId}")
            DiscordResult.SUCCESS
        }

    }

}
```
Agora você deve registrá-lo. Para isso você deve ir em seu **Client** e registrar o evento com **DiscordClient#registerEvents(vararg events)**. Exemplo:
```kotlin
@ObsoleteCoroutinesApi
class HelloDiscord: DiscordClient("discordbot1", ".") {

    override var application = settings {
        token = "MVDSAOKkvb94958cvbSX.XhI6eg.NOTAREALTOKEN.GFDGF4543VFDKC_sdfkkj"
        status = IDLE
        activity = watching("Something...")
    }
   
    override var onReady = {
        println("Logado como ${discord.user.name}#${discord.user.discriminator}")
        registerEvents(HelloEvent()) //Você pode usar múltiplos eventos. Exemplo: registerEvents(Event1(), Event2())
    }()

}
```

#Criando comandos
Para criar um comando, você deve criar uma classe na qual implementa **DiscordCommand()** e então dar override no método **settings** inserindo as informações do comando e também no método **run**, na qual você cria o código de sua aplicação. Assim como os eventos, você deve retornar um ENUM de **DiscordResult**. Exemplo:
```kotlin
@ObsoleteCoroutinesApi
class Command(): DiscordCommand() {

    override val settings = settings {
        name = "comando"
        aliases = listOf("cmdalias")
        description = "E aí!" //Isso é opcional.
        usage = "Isso também é opcional!"
    }

    override fun run(client: DiscordClient, arguments: List<String>, event: GuildMessageReceivedEvent) = command(event) {
        reply(embed {
            title = "Oi!" to null
            color = Color.CYAN
            description = "..."
            fields = listOf(
                field(inline = true) {
                    "Olá" to "mundo!" //Título | Valor
                }
            )
        })
        DiscordResult.SUCCESS
    }

}
```
Agora iremos registrar em nossa classe. Para isso você deve utilizar:
```kotlin
DiscordClient#registerCommands(vararg commands)
```

#Criando embeds
Para criar um embed, você deve usar este código de base:
```kotlin
embed {
    title = "Oi!" to null //Título: URL do título
    color = Color.CYAN //Cor do embed
    description = "..." //Descrição do embed
    author = Triple("Tá, né '-'", null, null) //Author: URL: URL do ícone
    footer = "Opa..." to null //Footer: URL do ícone
    fields = listOf(
        field(inline = true) {
            "Olá" to "mundo!" //Título | Valor
        }
    )
}
```
**Observação: Todos os argumentos são opcionais**




<p><font size="1">Observação: A documentação ainda não foi concluída.</font></p>