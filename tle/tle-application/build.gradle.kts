tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    val springDocVersion = "2.0.4"

    implementation(project(":tle:tle-domain"))

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("io.micrometer", "micrometer-tracing")
    implementation("io.micrometer", "micrometer-tracing-bridge-brave")

    implementation("org.springframework.boot:spring-boot-devtools")

    implementation("org.springdoc", "springdoc-openapi-starter-common", springDocVersion)
    implementation("org.springdoc", "springdoc-openapi-starter-webmvc-ui", springDocVersion)

    runtimeOnly("org.postgresql:postgresql")

    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-gson:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("com.h2database:h2")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.3")
    }
}