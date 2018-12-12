package core

class Benchmark[A](buildIterable: Unit => Iterable[A], f: Iterable[A] => Unit) {
  val numberOfRuns = 1000

  def run(): BenchmarkResult = {
    val stats = new Stats
    warmup()

    (0 until numberOfRuns).foreach{ i =>
      stats.push(oneExecution())
    }

    BenchmarkResult(stats.min.get, stats.max.get, stats.mean(), stats.variance(), stats.standardDeviation())
  }

  def warmup(): Unit = {
    (0 until 100).foreach{ i =>
      oneExecution()
    }
  }

  def oneExecution(): Long = {
    val iterable = buildIterable()
    val startTime = System.currentTimeMillis()
    f(iterable)
    val endTime = System.currentTimeMillis()
    endTime - startTime
  }

}

case class BenchmarkResult(min: Double, max: Double, average: Double, variance: Double, standardDeviation: Double)
