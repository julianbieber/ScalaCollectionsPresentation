package core

object Example4 extends App {
  Seq(1, 2, 3).map{ i =>
    println("method1", i)
    i
  }.map{ i =>
    println("method2", i)
    i
  }
}
