package core

object example2 extends App {
  val stream = Stream(1, 2, 3, 4, 5)

  println(stream)

  val seq: Seq[Int] = stream.toSeq

  seq.map(println)
}
