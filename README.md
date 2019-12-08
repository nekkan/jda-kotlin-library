# JDA-kt
Crie bots em JDA em Kotlin com facilidade usando o JDA-kt.


# Instalação
- O código do JDA-kt ainda não foi finalizado, então, a primeira build ainda não foi liberada. Em breve liberaremos o download em Gradle, Maven, etc, junto do código-fonte.


# Ligando o bot
Para ligar o bot, você deve usar `JDA#build("token", awaitReady)` (*awaitReady: Boolean = Se quer que o bot faça uma interrupção no código até terminar de ligá-lo. Util para eventos e comandos*). 


# Criando embeds (DSL)
Para criar um embed, você deve criar uma DSL simples, alterando os valores das variáveis. Todas as variáveis são opcionais (ou seja, não há valores obrigatórios a serem colocados). As variáveis disponíveis são: `content, color, title, author, authorUrl, authorIconUrl, description, fields, footer, footerIconUrl`.

Para criar um field, você precisa criar um mapa dos tipos *String* e *Boolean*: String = título e valor | Boolean = inline.
No mapa, em String, para separar o título do valor você precisa usar `"título||valor"`. Lembrando que também é permitido usar `\n` no valor. Exemplo:
```kotlin
val e = embed {
  content = member.asMention
  color = Color.decode("#ffffff")
  title = "Olá!"
  author = "${member.user.name}#${member.user.discriminator}"
  //Author URL não será colocado pois não há motivos, como eu disse, tudo é opcional, mas você não pode criar um embed vazio.
  authorIconUrl = member.user.avatarUrl
  description = "Olá, servidor!\nIsto é um embed."
  fields = mapOf<String, Boolean>("Oi.||Apenas testando! :P" to false, "Mais um campinho.||Eaí!" to false)
  footer = "github.com/nekkan"
}
```


# Criando comandos com facilidade (DSL).
Há dois modos de você criar um comando. O primeiro modo é o padrão, no qual você usa vários parâmetros para citar o prefixo, o nome do comando, suas aliases, mas nesta forma você não pode acessar seu builder e não pode usar descrição, usage (uso correto), etc. O segundo modo é usando o builder que eu inclui no código, no qual também é uma DSL mas você tem acesso a ele e você pode usar descrição, usage (uso correto), além de ter a variável `extra` disponível (opcional), na qual você pode setar um valor customizado, depois você pode acessá-la usando `CommandCreator#extra`.

As variáveis disponíveis do builder são `prefix, name, aliases, description, usage, extra`. As únicas variáveis obrigatórias são `prefix` e `name`.

Os parâmetros disponíveis para fazer um comando sem builder são `prefix, name, aliases`. O único parâmetro que não é obrigatório é o `aliases`.

Para ambos os modos de fazer, você necessita de um parâmetro inicial para fazer o comando, no qual é o `JDA`. Os comandos são registrados automaticamente após serem instanciados.

Exemplos:

**Usando o builder (Recomendado):**

```kotlin
val jda: JDA = build("...", true)

val b = creator {
  prefix = "-"
  name = "ping"
  aliases = listOf("pong")
  description = "Responde a mensagem com 'pong'."
  //Caso o usage não seja setado, será setado automaticamente para $prefix$name. Por isso não usarei.
  //Não usarei o extra pois não há motivo, como eu disse, ele é opcional, assim como todas as variáveis menos o prefix e o name.
}

command(jda, b) { it ->
  it.channel.msg("Pong! Descrição do comando: ${b.description}") //Mandar mensagem de forma async (msg("mensagem")).
  it.channel.message("Pong!!!") //Mandar mensagem em forma não async, ou seja, apenas uma abreviação pro .queue(). Você também pode usar o { -> } nele.
}
```


**Sem o builder (Forma simples):**
```kotlin
val jda: JDA = build("...", true)

command(jda, "-", "ping", listOf("pong") { it ->
  it.channel.msg("Pong!") //Mandar mensagem de forma async (msg("mensagem")).
  it.channel.message("Pong!!!") //Mandar mensagem em forma não async, ou seja, apenas uma abreviação pro .queue(). Você também pode usar o { -> } nele.
}
```


# Eventos (DSL):
Para criar seus eventos de forma rápida e simples, você só precisa utilizar `JDA#on<Event>` (ou caso for um evento de atualização: `JDA#onUpdate<updateEvent`).

Exemplo:
```kotlin
val jda: JDA = build("...", true)

jda.on<GuildMessageDeleteEvent> { it ->
  println("Mensagem excluída! ID: ${it.messageIdLong}")
}
```

# Async:
Para mandar mensagens de forma assíncrona, você pode usar `MessageChannel#msg(conteudo)`.

Para editar mensagens de forma assíncrona, você pode usar `Message#edit(novo_conteudo)`.

Para reagir a mensagens de forma assíncrona, você pode usar `Message#react(remover_reacoes_antigas, emojis_unicode)`. Exemplo:
`msg.react(false, "🏓", "✌") //Ps: Você pode adicionar apenas um emoji, mas eu mostrei com 2 para um exemplo`. Para remover as reações antigas o bot precisa de permissão para isso.

Para parar o tempo (dar delay) sem interferir em outras funções sem usar uma função suspend, você pode usar `pause(long)`.


# Ações repetidas (DSL):
Você pode repetir ações de forma simples usando `repeatingTask(int(Quantas vezes será repetido), delayMillis) { //código// }`

Exemplo:
```kotlin
var i = 0
repeatingTask(10, 1000) { //Será repetida 10 vezes a cada 1 segundo (1000ms).
  i++
  println(i)
}
```


# Utilidades:
Para verificar se uma lista de *Strings* (`List<String>`) possui um certo valor em case-insensitive, ou seja, *ignoreCase* sem usar o *for* ou outras coisas que podem travar sua aplicação, você pode usar `List<String>#containsIgnoreCase(conteudo)`.

Para ver a diferença de tempo entre a criação de uma mensagem e outra, você pode usar `Message#timeDifference(outra_mensagem)`.

Para obter uma mensagem com o conteúdo atualizado (após ela ser editada por exemplo), você pode usar `val msg = msg.refreshContent()`.

Para deletar uma mensagem depois de *tal* tempo em *ms*, você pode usar `Message#deleteAfter(millis)`.

Para verificar se o conteúdo da mensagem é igual a de outra mensagem (*raw*) você pode usar `Message#isEquals(outra_mensagem, ignoreCase)`. Exemplo: `msg.isEquals(msg2, true)`.

Para conseguir um canal de texto de forma *nullable*, você pode usar `Guild#nullableTextChannelById(id)`. Já um canal de voz, você pode usar `Guild#nullableVoiceChannelById(id)`.


# Exemplos:
Aqui estão alguns códigos de exemplo:

> **Comando de ping e logs:**
```kotlin
package com.github.nekkan.jdakt

import com.github.nekkan.jdakt.dsl.command.command
import com.github.nekkan.jdakt.dsl.command.creator
import com.github.nekkan.jdakt.dsl.event.on
import com.github.nekkan.jdakt.utils.async.*
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class Testy {

    private val messages = mutableMapOf<Long, Message>()

    init {

        val bot = Main.jda
        val v = "✌"
        val pong = "🏓"

        val creator = creator {
            prefix = "-"
            name = "ping"
            aliases = listOf("pong")
            description = "Mostra o ping do bot."
            usage = "$prefix$name"
            extra = "Informação extra!"
        }

        command(bot, creator) { it ->
            it.channel.msg("Hi!") //Async
            it.channel.message("Hii!") //Not async
            it.channel.message("Ping!") { msg -> //Not async (message isn't async)
                msg.edit("**Pong!** *(${it.message.timeDifference(msg)}) ms*") //Async (edit & msg = async)
                msg.react(false, "✌") //Async reaction.
            }
            it.channel.sendMessage("'-'").async() //Another way to send async message.
        }

        bot.on<GuildMessageReceivedEvent> { it ->
            if(it.author.isBot) return@on
            messages[it.messageIdLong] = it.message
        }

        bot.on<GuildMessageDeleteEvent> { it ->
            if(messages[it.messageIdLong]?.contentRaw.isNullOrEmpty()) return@on
            it.channel.msg("Content: ${messages[it.messageIdLong]?.contentRaw}")
            messages.remove(it.messageIdLong)
        }
    
}}
```


# Documentação
- A documentação não foi concluída, mas você pode achá-la aqui no GitHub mesmo (ainda não foi criada).

