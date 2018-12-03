package core

object Example8 extends App {

  val iterator = Stream.from(1).map{ i =>
    println("method1", i)
    i
  }.map{ i =>
    println("method2", i)
    i
  }.toIterator

  println(iterator.sum)

}
