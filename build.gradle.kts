plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.allopen") version "1.6.21"
    id("io.quarkus")
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    /**
     * Kotlin
     */
    implementation("io.quarkus:quarkus-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    /**
     * Dependency Injecction
     */
    implementation("io.quarkus:quarkus-arc")
    /**
     * Vert.x
     */
    implementation("io.quarkus:quarkus-vertx")
    implementation("io.quarkus:quarkus-vertx-web")
    /**
     * Container Image
     */
    implementation("io.quarkus:quarkus-container-image-jib")
    /**
     * Kubernetes
     */
    implementation("io.quarkus:quarkus-kubernetes")
    implementation("io.quarkus:quarkus-kubernetes-config")
    /**
     * Reactive
     */
    implementation("io.quarkus:quarkus-mutiny")
    implementation("io.quarkus:quarkus-reactive-routes")
    /**
     * RESTEasy Reactive
     */
    implementation("io.quarkus:quarkus-resteasy-reactive")
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    /**
     * REST Client Reactive
     */
    implementation("io.quarkus:quarkus-rest-client-reactive")
    implementation("io.quarkus:quarkus-rest-client-reactive-jackson")
    /**
     * Fault Tolerance
     */
    implementation("io.quarkus:quarkus-smallrye-fault-tolerance")
    /**
     * Health Check
     */
    implementation("io.quarkus:quarkus-smallrye-health")
    /**
     * Logging
     */
    implementation("io.quarkus:quarkus-logging-gelf")
    implementation("io.quarkus:quarkus-smallrye-opentracing")
    /**
     * Security
     */
    implementation("org.bouncycastle:bcprov-jdk15on:1.70")
    implementation("org.bouncycastle:bcpkix-jdk15on:1.70")
    implementation("io.quarkus:quarkus-oidc")
    /**
     * Others
     */
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.1")
    /**
     * Test
     */
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}

group = "ph.opensource"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
    kotlinOptions.javaParameters = true
}
