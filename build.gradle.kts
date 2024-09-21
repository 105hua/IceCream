plugins{
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.0.20"
    id("com.diffplug.spotless") version "7.0.0.BETA2"
    id("com.gradleup.shadow") version "8.3.2"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "com.nearvanilla"
version = "1.0"

repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.incendo:cloud-paper:2.0.0-beta.10")
    implementation("org.incendo:cloud-annotations:2.0.0")
    annotationProcessor("org.incendo:cloud-annotations:2.0.0")
}

val targetJavaVersion = 21

java {
    var javaVersion = JavaVersion.toVersion(targetJavaVersion)
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

spotless {
    format("misc"){
        target(listOf("**/*.gradle", "**/*.md"))
        trimTrailingWhitespace()
        indentWithSpaces(4)
    }
    kotlin{
        ktlint("1.3.1").editorConfigOverride(
            mapOf(
                "max_line_length" to "120"
            )
        )
        licenseHeader("/* Licensed under the GNU Affero General Public License. */")
    }
}

tasks.withType(JavaCompile::class).configureEach {
    options.encoding = "UTF-8"
    if(targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible){
        options.release.set(targetJavaVersion)
    }
}

tasks{
    shadowJar{
        isEnableRelocation = true
        relocationPrefix = "${rootProject.property("group")}.${rootProject.property("name").toString().lowercase()}.lib"
        minimize()
        archiveClassifier.set("")
    }
    build{
        dependsOn("shadowJar")
    }
    runServer{
        minecraftVersion("1.21.1")
        jvmArgs("-Dcom.mojang.eula.agree=true")
    }
}