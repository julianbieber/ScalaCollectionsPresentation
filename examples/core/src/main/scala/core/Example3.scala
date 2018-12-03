package core

object Example3 extends App {

  val stream = Stream(1, 2, 3)

  stream.foreach(println)

  val streamPlusOne = stream.map(_ + 1)

  println(stream.size)

  streamPlusOne.foreach(println)
}
