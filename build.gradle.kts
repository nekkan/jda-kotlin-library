plugins {
    kotlin("jvm") version "1.3.61"
    maven
}

group = "com.github.nekkan"
version = "2.0.0"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    compile("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.3.3")
    compile("net.dv8tion", "JDA", "4.1.0_90")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
