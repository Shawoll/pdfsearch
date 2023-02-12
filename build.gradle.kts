plugins {
    `java`
    `application`
}

group = "com.example"
version = "1.0-SNAPSHOT"

compileJava {
    sourceCompatibility = JavaVersion.VERSION_19
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.pdfbox:pdfbox:2.0.22")
}

application {
    mainClassName = "com.example.Main"
}

tasks.register("jlink", Exec::class) {
    commandLine("jlink",
        "--module-path", "${System.getProperty("java.home")}/jmods",
        "--add-modules", "java.base,java.desktop,java.naming,java.sql,jdk.unsupported,org.apache.pdfbox",
        "--output", "jre"
    )
}

tasks.getByName("run").apply {
    jvmArgs("-XX:+UseContainerSupport", "-XX:+IdleTuningCompactOnIdle", "-XX:+IdleTuningGcOnIdle")
    jvmArgs("-Xmx512m")
    jvmArgs("-Xms512m")
    jvmArgs("-XX:InitialRAMPercentage=50")
    jvmArgs("-XX:MaxRAMPercentage=80")
    jvmArgs("-Djava.home=./jre")
}