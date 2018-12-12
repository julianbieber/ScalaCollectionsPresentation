package core

import scala.reflect.ClassTag

object Main extends App {

  def buildList[A](gen: Int => A, size: Int): Unit => List[A] = { _ =>
    (0 until size).map(gen).toList
  }

  def buildArray[A](gen: Int => A, size: Int)(implicit classTag: ClassTag[A]): Unit => Iterable[A] = { _ =>
    val array = new Array[A](size)
    (0 until size).foreach{ i =>
      array(i) = gen(i)
    }
    array
  }

  def buildVector[A](gen: Int => A, size: Int): Unit => Vector[A] = { _ =>
    (0 until size).map(gen).toVector
  }

  def buildStream[A](gen: Int => A, size: Int): Unit => Stream[A] = { _ =>
    (0 until size).map(gen).view.toStream
  }

  def iteration[A](iterable: Iterable[A]): Unit = {
    iterable.foreach(_ => ())
  }

  def map[A, B](f: A => B)(iterable: Iterable[A]): Unit = {
    val result = iterable.map(f)
  }

  def viewMap[A](f: A => A)(iterable: Iterable[A]): Unit = {
    val results = iterable.view
      .map(f)
      .map(f)
      .map(f)
      .map(f)
      .map(f)
      .force
  }

  def viewMapStream[A](f: A => A)(iterable: Iterable[A]): Unit = {
    val results = iterable.asInstanceOf[Stream[A]]
      .map(f)
      .map(f)
      .map(f)
      .map(f)
      .map(f)
      .force
  }

  def viewMapOne[A](f: A => A)(iterable: Iterable[A]): Unit = {
    val results = iterable.view
      .map(f)
      .force
  }

  def parMap[A, B](f: A => B)(iterable: Iterable[A]): Unit = {
    val result = iterable.par.map(f).seq
  }

  def runBenchmark[A](name: String, buildIterable: Int => Unit => Iterable[A], f: Iterable[A] => Unit): Unit = {
    println(s"$name 1000000    ${new Benchmark[A](buildIterable(1000000), f).run()}")
    println(s"$name 10000000   ${new Benchmark[A](buildIterable(10000000), f).run()}")
  }
/*
  runBenchmark("List[Int].foreach", buildList({ i: Int => i }, _), iteration)
  runBenchmark("Array[Int].foreach", buildArray({ i: Int => i }, _), iteration)
  runBenchmark("Vector[Int].foreach", buildVector({ i: Int => i }, _), iteration)
  runBenchmark("Stream[Int].foreach", buildStream({ i: Int => i }, _), iteration)
  println()

  runBenchmark("List[Int].map", buildList({ i: Int => i }, _), map[Int, Int](a => a + 1))
  runBenchmark("Array[Int].map", buildArray({ i: Int => i }, _), map[Int, Int](a => a + 1))
  runBenchmark("Vector[Int].map", buildVector({ i: Int => i }, _), map[Int, Int](a => a + 1))
  runBenchmark("Stream[Int].map", buildStream({ i: Int => i }, _), map[Int, Int](a => a + 1))
  println()

  runBenchmark("List[Int].viewMap one transformation", buildList({ i: Int => i }, _), viewMapOne[Int](a => a + 1))
  runBenchmark("Array[Int].viewMap one transformation", buildArray({ i: Int => i }, _), viewMapOne[Int](a => a + 1))
  runBenchmark("Vector[Int].viewMap one transformation", buildVector({ i: Int => i }, _), viewMapOne[Int](a => a + 1))
  runBenchmark("Stream[Int].viewMap one transformation", buildStream({ i: Int => i }, _), viewMapOne[Int](a => a + 1))
  println()

  runBenchmark("List[Int].parMap", buildList({ i: Int => i }, _), parMap[Int, Int](a => a + 1))
  runBenchmark("Array[Int].parMap", buildArray({ i: Int => i }, _), parMap[Int, Int](a => a + 1))
  runBenchmark("Vector[Int].parMap", buildVector({ i: Int => i }, _), parMap[Int, Int](a => a + 1))
  runBenchmark("Stream[Int].parMap", buildStream({ i: Int => i }, _), parMap[Int, Int](a => a + 1))
  */
  //runBenchmark("List[Int].viewMap", buildList({ i: Int => i }, _), viewMap[Int](a => a + 1))
  //runBenchmark("Array[Int].viewMap", buildArray({ i: Int => i }, _), viewMap[Int](a => a + 1))
  //runBenchmark("Vector[Int].viewMap", buildVector({ i: Int => i }, _), viewMap[Int](a => a + 1))
  runBenchmark("Stream[Int].viewMap", buildStream({ i: Int => i }, _), viewMapStream[Int](a => a + 1))


}
