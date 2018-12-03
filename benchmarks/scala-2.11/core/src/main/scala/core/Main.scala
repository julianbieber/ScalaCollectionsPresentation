package core

import org.scalacheck.Gen

import scala.reflect.ClassTag

object Main extends App {
  def buildSeq[A](gen: Gen[A], size: Int): Unit => Seq[A] = { _ =>
    (0 until size).foldLeft(Seq[A]()){case (accumulator, _) => accumulator ++ Seq(gen.sample.get)}
  }

  def buildList[A](gen: Gen[A], size: Int): Unit => List[A] = { _ =>
    (0 until size).foldLeft(List[A]()){case (accumulator, _) => accumulator ++ List(gen.sample.get)}
  }

  def buildArray[A](gen: Gen[A], size: Int)(implicit classTag: ClassTag[A]): Unit => Iterable[A] = { _ =>
    val array = new Array[A](size)
    (0 until size).foreach{ i =>
      array(i) = gen.sample.get
    }
    array
  }

  def buildVector[A](gen: Gen[A], size: Int): Unit => Vector[A] = { _ =>
    (0 until size).foldLeft(Vector[A]()){case (accumulator, _) => accumulator ++ Vector(gen.sample.get)}
  }

  def buildStream[A](gen: Gen[A], size: Int): Unit => Stream[A] = { _ =>
    (0 until size).foldLeft(Stream[A]()){case (accumulator, _) => accumulator ++ Stream(gen.sample.get)}
  }

  def iteration[A](iterable: Iterable[A]): Unit = {
    iterable.foreach(_ => ())
  }

  def map[A, B](iterable: Iterable[A], f: A => B): Unit = {
    val result = iterable.map(f)
  }

  def runBenchmark[A](name: String, buildIterable: Int => Unit => Iterable[A], f: Iterable[A] => Unit): Unit = {
    println(s"$name 1000  ${new Benchmark[A](buildIterable(1000), f).run()}")
    println(s"$name 100000 ${new Benchmark[A](buildIterable(100000), f).run()}")
    println(s"$name 10000000 ${new Benchmark[A](buildIterable(10000000), f).run()}")
  }

  runBenchmark("Seq[Long].foreach", buildSeq(Gen.posNum[Long], _), iteration)
  runBenchmark("List[Long].foreach", buildList(Gen.posNum[Long], _), iteration)
  runBenchmark("Array[Long].foreach", buildArray(Gen.posNum[Long], _), iteration)
  runBenchmark("Vector[Long].foreach", buildVector(Gen.posNum[Long], _), iteration)
  runBenchmark("Stream[Long].foreach", buildStream(Gen.posNum[Long], _), iteration)

}
