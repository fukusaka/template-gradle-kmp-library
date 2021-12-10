plugins {
    kotlin("multiplatform") version "1.6.0"
    id("maven-publish")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }

    val projectArtifactId = "hello-kmp"
    val validPublications = listOf(jvm(), js()).map { it.name } + "kotlinMultiplatform"

    publishing {
        publications {
            withType<MavenPublication> {
                artifactId = when (name) {
                    "kotlinMultiplatform" -> projectArtifactId
                    else -> "$projectArtifactId-$name"
                }
            }

            afterEvaluate {
                matching { it.name !in validPublications }.all {
                    val targetPublication = this
                    tasks.withType<AbstractPublishToMaven>()
                        .matching { it.publication == targetPublication }
                        .configureEach { enabled = false }
                }
            }
        }

        repositories {
            val uploadMavenUser = project.findPropertyAsString("uploadMavenUser")
            val uploadMavenPassword = project.findPropertyAsString("uploadMavenPassword")

            if (uploadMavenUser != null && uploadMavenPassword != null) {
                maven {
                    name = "GitHubPackages"
                    url = uri("https://maven.pkg.github.com/fukusaka/template-gradle-kmp-library")
                    credentials {
                        username = uploadMavenUser
                        password = uploadMavenPassword
                    }
                }
            }
        }

    }
}

// https://youtrack.jetbrains.com/issue/KT-49109
rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
    val nodeM1Version = "16.13.1"
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().nodeVersion = nodeM1Version
}
