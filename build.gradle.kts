plugins {
  id("net.kyori.indra.publishing.sonatype") version "3.1.1"
}

indraSonatype {
  useAlternateSonatypeOSSHost("s01")
}
