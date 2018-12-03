package core

object Example7 extends App {

  val stream = Stream.from(1).map{ i =>
    println("method1", i)
    i
  }.map{ i =>
    println("method2", i)
    i
  }

  println(stream.sum)

}
