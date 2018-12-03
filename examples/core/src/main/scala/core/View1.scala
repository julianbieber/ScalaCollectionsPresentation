package core

object View1 extends App {

  val seq: Seq[Int] = Seq(1, 2, 3).view.map{ i =>
    println("method1", i)
    i
  }.map{ i =>
    println("method2", i)
    i
  }

}
