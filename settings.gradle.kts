pluginManagement {
  repositories {
    gradlePluginPortal()
  }

  includeBuild("build-logic")
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

rootProject.name = "event-parent"

sequenceOf(
  "event-api"
).forEach {
  include(it)
}
