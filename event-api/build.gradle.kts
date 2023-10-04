plugins {
  id("event.conventions")
}

dependencies {
  compileOnlyApi("org.jetbrains:annotations:24.0.1")
  compileOnlyApi("org.jspecify:jspecify:0.3.0")
  testImplementation("com.google.guava:guava-testlib:32.1.2-jre")
}
