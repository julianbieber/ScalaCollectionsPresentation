package core

object View2 extends App {

  println(function(Seq(1, 2, 3).view.map{ i =>
    println("method1", i)
    i
  }.map{ i =>
    println("method2", i)
    i
  }))

  def function(seq: Seq[Int]): Seq[CaseClass] = {
    seq.map(CaseClass)
  }
}

case class CaseClass(member: Int) {
  println("CaseClass", member)
}