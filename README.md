[![](https://jitpack.io/v/nekkan/jda-kotlin-library.svg)](https://jitpack.io/#nekkan/jda-kotlin-library) 

# jda-kotlin-library
A função principal do **jda-kotlin-library** é lhe auxiliar na criação de eventos, comandos, embeds, entre outras coisas para facilitar seu código e deixá-lo com uma ótima legibilidade com o JDA.

### Sumário
O **jda-kotlin-library** não interfere no utilizamento do JDA, não viola os ToS do Discord, não coleta quaisquer dado e está livre para modificações desde que qualquer alteração esteja pública.

- [Download](#download)
- [Por que utilizar?](#por-que-utilizar)
- [Client](#criando-um-client)
- [Eventos](#criando-eventos)
- [Comandos](#criando-comandos)
- [Embeds](#criando-embeds)
- [Condições](#condições)
- [Fails](#fails)

### Download
Você pode obter o **jda-kotlin-library** em Gradle, Maven, SBT e em Leiningen no Jitpack! [Clique aqui!](https://jitpack.io/#nekkan/jda-kotlin-library)

### Pré-requisitos
- [JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html) de no mínimo versão 8 (Java Development Kit). 

### Dependências
- **[JDA](https://github.com/DV8FromTheWorld/JDA)** (obrigatório)
- **[Kotlin Coroutines](https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core)** (obrigatório)
- **[Kotlin Reflect](https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-reflect)** (obrigatório)

# Por que utilizar?
O **jda-kotlin-library** faz todos os eventos e comandos em **async**, além de adicionar facilidade e legibilidade em seus códigos.

# Criando um client
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

# Criando eventos
Para criar um evento, você deve criar uma classe na qual implementa **DiscordEvent()**. Em seguida você deve usar o **init** do Kotlin e usar o método **event<T>()** e dentro do mesmo inserir seu código, lembrando que no T você deve substituir pela classe do evento. Em seguida você deve retornar um valor do ENUM **DiscordResult**. Exemplo:
```kotlin
class HelloEvent: DiscordEvent() {

    init {

        event<GuildMessageReactionAddEvent> { event, client ->
            println("Uma reação foi adicionada a mensagem com o ID ${event.messageId}")
            DiscordResult.SUCCESS
        }
//Lembrando que em todos os eventos de mensagem você pode usar o reply() e nessa classe de eventos você pode colocar quantos eventos quiser.d

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

# Criando comandos
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

# Criando embeds
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

# Condições
O *AnyMap*, como o nome diz, é um map no qual pode ser utilizado em qualquer lugar, já o map padrão do Kotlin permite apenas em Strings
*(ps: não estou referindo-me aos maps de Map.put())*.

Darei um exemplo a vocês do mesmo de dois códigos; Um no qual utiliza os ifs e elses, deixando o código maior:
```kotlin

//Utilizando ifs.
event { event, client ->

    if(event.author.isBot) return DiscordResult.ERROR
    if(!event.message.contentRaw.startsWith("!")) return DiscordResult.ERROR
    
    val args = event.message.contentRaw.toLowerCase().split(" ")
    if(!args[0].equals("!ping")) return DiscordResult.ERROR
    
    event.reply("Pong!")
    DiscordResult.SUCCESS
}

//Utilizando condições do jda-kotlin-library (AnyMap)
event { event, client ->
    //Uso correto: map<T> { }. O <T> é opcional caso o Kotlin detecte o tipo.
    map { event }.
    filter { !it.author.isBot }?.
    map { it.message.contentRaw }?.
    filter { it.startsWith("!") }?.
    map { it.toLowerCase().split(" ") }?.
    filter { it[0] == "!ping" }?
    .subscribe(async = true) {
            event.reply("Pong!")
        } //Deixei em várias linhas para entender mais fácil.
    DiscordResult.SUCCESS
}
```

# Fails
O uso dos Fails é caso não seja possível conseguir um argumento de uma lista, array ou String. Caso não seja possível, por padrão ele dará um Throw Error, mas você pode escolher o que ele fará caso não seja possível pegar tal elemento da lista.
Os elementos da lista pegos não serão 0-based, ou seja, não serão com base no 0. Então caso você queira pegar args[0], no Fail você usaria args[1].
Depois que tal tarefa for executada, o mesmo retornará **DiscordResult.ERROR**.

Para isso, você deve usar **safe-arguments**, no qual é um argumento que pode ser pego, e caso não seja possível pois não existe nada na lista com esse index, ele retornará null e não uma exception.

```kotlin
//EXEMPLOS:

//Sem o uso dos fails:
try {
    val member = listaDeMembros[6554]
    reply("Membro obtido!")
    return DiscordResult.SUCCESS
} catch(exception: AlgumaException) {
    println("Não foi possível obter um membro.")
    reply("Erro!")
    return DiscordResult.ERROR
}

//Com o uso dos fails:
val member = argument(listaDeMembros, 6555) ?: return fail {
    println("Não foi possível obter um membro.")
    reply("Erro!")
    //Recomendado em eventos e comandos.
}
reply("Membro obtido!")
return DiscordResult.SUCCESS
```

----
<sub><sup>A documentação ainda não foi concluída.</sub></sup>
