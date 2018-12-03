package core

object Example5 extends App {
  Stream(1, 2, 3).map{ i =>
    println("method1", i)
    i
  }.map{ i =>
    println("method2", i)
    i
  }
}
