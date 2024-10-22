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

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    compileOnly(libs.mapstruct) //note: mapstruct always be placed after lombok cause conflict
    annotationProcessor(libs.mapstruct.processor)
    annotationProcessor(libs.lombokMapstructBinding)
    implementation(libs.spring.configProcessor)
    implementation(libs.postgreSql)
    implementation(libs.spring.starter.web)
    implementation(libs.spring.starter.jetty)
    implementation(libs.spring.starter.validation)
    implementation(libs.spring.starter.data.jdbc)
    implementation(libs.spring.starter.security)
    implementation(libs.io.jwt.api)
    runtimeOnly(libs.io.jwt.impl)
    runtimeOnly(libs.io.jwt.jackson)
    implementation(libs.spring.starter.modulith.core)
    implementation(libs.spring.modulith.event.api)
    implementation(libs.spring.modulith.event.jackson)
    implementation(libs.spring.starter.modulith.jdbc)
    implementation(libs.spring.doc.openapi)
    developmentOnly(libs.spring.devTools)
    runtimeOnly(libs.spring.docker)
    testImplementation(libs.spring.starter.modulith.test)
    modules {
        module(libs.spring.starter.tomcat.get().module) {
            replacedBy(libs.spring.starter.jetty.get().module)
        }
    }
}

group = "com.dd"
version = "0.0.1"

dependencyManagement {
    imports {
        mavenBom(libs.spring.modulith.bom.get().toString())
    }
}