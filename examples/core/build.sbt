name := "core"

libraryDependencies ++= LibraryDependencies.root

enablePlugins(PackPlugin)

val standardJVMOpts = Seq(
  "-XX:+UnlockExperimentalVMOptions",
  "-XX:+UseCGroupMemoryLimitForHeap",
  "-XX:MaxRAMFraction=2"
)

val mains = Seq(
  "example1",
  "example2",
  "example3",
  "example4",
  "example5",
  "example6",
  "example7",
  "example8",
  "view1",
  "view2"
)


packJvmOpts := mains.map{ main => main -> standardJVMOpts}.toMap