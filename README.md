[version]: https://raster.shields.io/badge/Versão-1.3.1-yellow.svg
[jitpack]: https://raster.shields.io/badge/Download-Jitpack-blue.svg
[jitpackurl]: https://jitpack.io/#nekkan/JDA-kt

![version] [![jitpack][]][jitpackurl]

# JDA-kt

A função principal do JDA-kt é lhe auxiliar na criação de eventos, comandos, embeds, entre outras coisas para facilitar seu código e deixá-lo com uma ótima legibilidade com o JDA.


# Instalação
- Você pode obter o JDA-kt em Gradle, Maven, SBT e em Leiningen no Jitpack! [Clique aqui!](https://jitpack.io/#nekkan/JDA-kt)
- Você precisa das dependências: 
- - **[JDA](https://github.com/DV8FromTheWorld/JDA)** (obrigatório);
- - **[Kotlin Coroutines](https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-android)** (obrigatório).


# Ligando o bot
Para ligar o bot, você deve usar por exemplo:
```kotlin
val jda: JDA = buildBot(token = "token") {
    activity { Activity.playing("Olá!") }
    onlineStatus { OnlineStatus.ONLINE }
    listeners { /* ListenerAdapter's */ }
    modules { listOf<Module>(/* Módulos */) }
}
//Você não precisa usar todos os valores, como por exemplo activity, onlineStatus, etc.
```


# Criando um módulo (comandos e eventos com facilidade) [DSL]:
Para usar comandos e eventos de forma simples, você precisa criar um módulo.

Para criar um módulo, você deve criar uma classe e extender `Module()`. Exemplo:
```kotlin
class Exemplo : Module()
```

Agora, dentro dela já podemos fazer nossos comandos e eventos, mas primeiro, devemos dar override no método 'module', no qual nele criaremos nossos comandos e eventos. O tipo de retorno que nós precisamos é ModuleBuilder, para criá-lo, devemos usar `return builder {}`. Exemplo:
```kotlin
class Exemplo : Module() {

    override fun module(module: ModuleBuilder.() -> Unit): ModuleBuilder = builder {}

}
```

Ótimo! Agora vamos criar um comando. Para isso, nós devemos criar um CommandBuilder para o mesmo, no qual é possível criar deste jeito:
```kotlin
val builder = commandBuilder {
    prefix = { guild -> "Algo que use a guild, como por exemplo, ${guild.prefix}." } //Caso não for usar nada com a guild: prefix = { "prefixo" }
    name = "nome do comando"
    aliases = listOf("aliases", "alias") //Opcional
    description = "Descrição" //Opcional
    usage = "Uso correto" //Opcional
    extra = "Informação extra! Este é um campo no qual pode ser usado qualquer valor, não somente uma String, pois é um Any!" //Opcional
}
```

Em seguida, usamos `command(builder) {}`! Exemplo:
```kotlin
class Exemplo : Module() {

    override fun module(module: ModuleBuilder.() -> Unit): ModuleBuilder = builder {
        val builder = commandBuilder {
            prefix = { "." }
            name = "ping"
            description = "Retorna a mensagem 'pong'."
        }.let {
            command(it) { it ->
                it.channel.msg("Pong!")
            }
        }
    }

}
```

Agora vamos registrar o comando. Para isso, devemos ir até o nosso builder e colocar `modules {listOf(módulos)}`. Exemplo:
```kotlin
    val jda: JDA = buildBot(token = "token") {
        modules { listOf(
            Exemplo()
        )}
    }
```

Para usar um evento, ao invés de `command(builder) {}`, você deve usar: `event<tipo> {}`. Lembrando que você pode usar vários comandos e eventos no mesmo módulo sem nenhum problema. Exemplo:
```kotlin
event<GuildMessageDeleteEvent> { it ->
    println("Uma mensagem foi apagada no servidor com o ID ${it.guild.id}. ID da mensagem deletada: ${it.messageId}")
}
```


# Criando embeds [DSL]:
Para criar um embed usando uma DSL, é muito simples, basta utilizar:
```kotlin
val embed = embed { 
    title { "Título" }
    author("texto", "url", "url do icone")
    footer("url") { "texto" }
    color { /* Cor do embed */ Color.decode("#abcdef") }
    image { "url" }
    thumbnail { "url" }
}
```

Para adicionar um campo a um embed, você deve usar: `entry(inline = false/true, title = "título") {"descrição"}`. Exemplo:
```kotlin
val embed = embed {
    title { "Título" }
    author("texto", "url", "url do icone")
    footer("url") { "texto" }
    color { /* Cor do embed */ Color.decode("#abcdef") }
    image { "url" }
    thumbnail { "url" }
    entry(inline = true, title = "Título") {
        "Olá!"
    }
}
```

Para enviar o embed, você pode mandar para um canal dando um build, ou simplesmente usando `embed{}.send(canal)`.


# Data Tags em mensagens:
Usar as data tags em mensagens usando o JDA-kt pode ser algo útil, por exemplo, verificar se tal mensagem contém tal tag sem interferir em nada. Por exemplo:
```kotlin
Message#addDataTag("tag")
Message#removeDataTag("tag")
Message#setDataTags(listOf("tag1", "tag2")) //Remove tudo e deixa apenas essas tags.
Message#dataTags
Message#containsDataTag("tag")
```

Isso pode ser útil para comandos de ajuda, suporte, tickets, etc.


# Utilidades:
Para verificar se uma lista de *Strings* (`List<String>`) possui um certo valor em case-insensitive, ou seja, *ignoreCase* sem usar o *for* ou outras coisas que podem travar sua aplicação, você pode usar `List<String>#containsIgnoreCase(conteudo)`.

Para ver a diferença de tempo entre a criação de uma mensagem e outra, você pode usar `Message#timeDifference(outra_mensagem)`.

Para obter uma mensagem com o conteúdo atualizado (após ela ser editada por exemplo), você pode usar `val msg = msg.refreshContent()`.

Para verificar se o conteúdo da mensagem é igual a de outra mensagem (*raw*) você pode usar `Message#isEquals(outra_mensagem, ignoreCase)`. Exemplo: `msg.isEquals(msg2, true)`.

Para conseguir um canal de texto de forma *nullable*, você pode usar `Guild#nullableTextChannelById(id)`. Já um canal de voz, você pode usar `Guild#nullableVoiceChannelById(id)`.

Para enviar mensagens de forma assíncrona, você pode usar `MessageChannel#msg(conteúdo)`. Por exemplo: `canal.msg("Oi!")`

# Documentação
- A documentação não foi concluída, mas você pode achá-la aqui no GitHub mesmo (ainda não foi criada).
