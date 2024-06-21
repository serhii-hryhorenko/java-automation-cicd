plugins {
    id("java")
    checkstyle
    `maven-publish`
}

checkstyle {
    toolVersion = "10.12.4"
}

group = "edu.ukma"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "edu.ukma"
            artifactId = "cicd"
            version = "1.0.0"
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/serhii-hryhorenko/java-automation-cicd")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required = false
        html.required = true
    }
}

//./gradlew checkFile -PfilePath=file.txt
tasks.register("checkFile") {
    doLast {
        val filePath = project.findProperty("filePath")?.toString() ?: throw GradleException("File path not provided.")
        val fileToCheck = file(filePath)
        if (fileToCheck.exists()) {
            println("File exists: ${fileToCheck.absolutePath}")
        } else {
            throw GradleException("File not found: ${fileToCheck.absolutePath}")
        }
    }
}

tasks.register<Copy>("copyAssets") {
    val sourceDir = file("${projectDir}/assets")
    val targetDir = file("${buildDir}/resources/main")
    targetDir.mkdirs();

    from(sourceDir)
    into(targetDir)

    doFirst {
        println("Copying assets from $sourceDir to $targetDir")
    }
}

tasks.named("processResources") {
    dependsOn("copyAssets")
}

tasks.test {
    useJUnitPlatform()
}
