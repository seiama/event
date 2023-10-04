plugins {
  id("event.conventions")
}

dependencies {
  compileOnlyApi("org.jetbrains:annotations:24.0.1")
  testImplementation("com.google.guava:guava-testlib:32.1.2-jre")
}
