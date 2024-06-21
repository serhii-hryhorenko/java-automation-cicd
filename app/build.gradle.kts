plugins {
    id("java")
}

group = "edu.ukma"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("com.sparkjava:spark-core:2.9.4")
    implementation("com.google.code.gson:gson:2.10.1")

    implementation(project(":core"))
    implementation(project(":service"))
}

tasks.test {
    useJUnitPlatform()
}