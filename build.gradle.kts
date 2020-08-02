plugins {
    java
    kotlin("jvm") version "1.4.0-rc"
    `maven-publish`
}

group = "com.leafclient"
version = "1.2.0"

repositories {
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven("http://jitpack.io")
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.LeafClient", "Trunk", "1.1.1")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
        }
    }
}