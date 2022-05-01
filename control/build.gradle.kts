group = "kalashnikov.academy"
version = "0.1"

plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kapt)
    alias(libs.plugins.serialization)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
}

repositories {
    mavenCentral()
}

sourceSets.main {
    dependencies {
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.bundles.ktor)
        implementation(libs.bundles.exposed)
        implementation(libs.bundles.logback)
        implementation(libs.dagger)
        kapt(libs.dagger.compiler)
    }
    java {
        srcDirs("src/main/kotlin")
    }
}

application.mainClass.set("academy.kalashnikov.control.ApplicationKt")
