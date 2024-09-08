tasks.getByName("bootJar") {
    enabled = false
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    val liquibaseVersion = "4.19.1"

//    compileOnly(project(":tle:tle-domain"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("org.liquibase", "liquibase-core", liquibaseVersion)

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("com.h2database:h2")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.3")
    }
}
