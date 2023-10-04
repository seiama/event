plugins {
  id("net.kyori.indra.publishing.sonatype") version "3.1.3"
}

indraSonatype {
  useAlternateSonatypeOSSHost("s01")
}
