plugins {
  id("event.conventions")
}

dependencies {
  compileOnlyApi("org.jetbrains:annotations:24.0.1")
  testImplementation("com.google.guava:guava-testlib:32.0.0-jre")
  testImplementation(platform("org.junit:junit-bom:5.9.3"))
  testImplementation("org.junit.jupiter:junit-jupiter-api")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}
