case class Point(x: Int, y: Int, verifier: (Int, Int, Int) => Unit = PointVerifier.run) {
  verifier(x, y, 10)
}

object Point {
  def stub(x: Int, y: Int): Unit ={
    new Point(x, y)
  }
}
