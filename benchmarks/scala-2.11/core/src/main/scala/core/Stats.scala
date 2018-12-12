package core

class Stats {
  var m_n = 0
  var m_oldM: Double = 0
  var m_newM: Double = 0
  var m_oldS: Double = 0
  var m_newS: Double = 0
  var min: Option[Double] = None
  var max: Option[Double] = None

  def push(x: Double) = {
    m_n = m_n + 1

    // See Knuth TAOCP vol 2, 3rd edition, page 232
    if (m_n == 1) {
      m_oldM = x
      m_newM = x
      m_oldS = 0.0
      min = Option(x)
      max = Option(x)
    }
    else {
      m_newM = m_oldM + (x - m_oldM) / m_n
      m_newS = m_oldS + (x - m_oldM) * (x - m_newM)

      // set up for next iteration
      m_oldM = m_newM
      m_oldS = m_newS

      if (x > max.get)
        max = Option(x)
      if (x < min.get)
        min = Option(x)
    }
  }


  def mean(): Double = {
    if (m_n > 0)
      m_newM
    else
      0.0
  }

  def variance(): Double = {
    if (m_n > 1) m_newS / (m_n - 1) else 0.0
  }

  def standardDeviation(): Double = {
    math.sqrt(variance())
  }


}
