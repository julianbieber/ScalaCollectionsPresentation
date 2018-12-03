name := "core"

libraryDependencies ++= LibraryDependencies.root

enablePlugins(PackPlugin)

packJvmOpts := Map("main" -> Seq(
  "-XX:+UnlockExperimentalVMOptions",
  "-XX:+UseCGroupMemoryLimitForHeap",
  "-XX:MaxRAMFraction=2"
))