package core

class Benchmark[A](buildIterable: Unit => Iterable[A], f: Iterable[A] => Unit) {
  val numberOfRuns = 10000

  def run(): BenchmarkResult q= {
    val results = new Array[Long](numberOfRuns)

    (0 until numberOfRuns).foreach{ i =>
      val iterable = buildIterable()

      val startTime = System.currentTimeMillis()
      f(iterable)
      val endTime = System.currentTimeMillis()

      results(i) = endTime - startTime
    }

    val average = results.sum / results.length
    val standardDeviation = math.sqrt(results.map(x => math.sqrt(x - average)).sum / results.length)
    BenchmarkResult(
      results.min,
      results.max,
      average,
      standardDeviation
    )
  }

}

case class BenchmarkResult(min: Long, max: Long, average: Double, standardDeviation: Double)