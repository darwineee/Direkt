import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    java
    alias(libs.plugins.springBoot)
    alias(libs.plugins.dependencyManagement)
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

val dockerUsername = project.findProperty("dockerUsername") as String?
val dockerToken = project.findProperty("dockerToken") as String?

tasks.named<BootBuildImage>("bootBuildImage") {
    imageName = "${dockerUsername}/direkt-messaging:${version}"
    publish = true
    docker {
        publishRegistry {
            username = dockerUsername
            password = dockerToken
        }
    }
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    compileOnly(libs.mapstruct) //note: mapstruct always be placed after lombok cause conflict
    annotationProcessor(libs.mapstruct.processor)
    annotationProcessor(libs.lombokMapstructBinding)
    implementation(libs.spring.configProcessor)
    implementation(libs.postgreSql)
    implementation(libs.spring.starter.web)
    implementation(libs.spring.starter.tomcat)
    implementation(libs.spring.starter.validation)
    implementation(libs.spring.starter.data.jdbc)
    implementation(libs.spring.starter.security)
    implementation(libs.spring.starter.websocket)
    implementation(libs.io.jwt.api)
    runtimeOnly(libs.io.jwt.impl)
    runtimeOnly(libs.io.jwt.jackson)
    implementation(libs.spring.security.messaging)
    implementation(libs.spring.starter.modulith.core)
    implementation(libs.spring.modulith.event.api)
    implementation(libs.spring.modulith.event.jackson)
    implementation(libs.spring.starter.modulith.jdbc)
    implementation(libs.spring.doc.openapi)
    developmentOnly(libs.spring.devTools)
    testImplementation(libs.spring.starter.modulith.test)
}

group = "com.dd"
version = "0.0.2-ALPHA"

dependencyManagement {
    imports {
        mavenBom(libs.spring.modulith.bom.get().toString())
    }
}