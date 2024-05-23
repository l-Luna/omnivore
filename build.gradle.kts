plugins {
    id("java")
    id("com.vanniktech.maven.publish") version "0.28.0"
}

group = "io.github.l-luna"
version = "0.3"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:24.1.0")
    
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}