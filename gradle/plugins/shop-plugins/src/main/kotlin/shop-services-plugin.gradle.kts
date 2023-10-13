plugins {
    `java-library`
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "ru.apmgor"
version = "0.0.1-SNAPSHOT"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    api(project(":common"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    api("org.springframework.boot:spring-boot-starter-test")
    api("org.springframework.boot:spring-boot-testcontainers")
    api("io.projectreactor:reactor-test")
    api("org.testcontainers:junit-jupiter")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }

        register<JvmTestSuite>("integrationTest") {
            dependencies {
                implementation(project())
            }
            targets {
                all {
                    testTask.configure {
                        shouldRunAfter(test)
                    }
                }
            }
        }

    }
}

tasks.named("check") {
    dependsOn(testing.suites.named("integrationTest"))
}