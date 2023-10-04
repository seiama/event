plugins {
  `kotlin-dsl`
}

dependencies {
  implementation("com.diffplug.spotless:spotless-plugin-gradle:6.21.0")
  implementation("net.kyori:indra-common:3.1.3")
  implementation("net.kyori:indra-git:3.1.3")
  implementation("net.kyori:indra-publishing-sonatype:3.1.3")
  implementation("net.ltgt.gradle:gradle-errorprone-plugin:3.1.0")
}
