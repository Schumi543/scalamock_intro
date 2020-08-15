object PointVerifier {
  def run(x: Int, y: Int, r: Int = 10): Unit = {
    require(x * x + y * y <= r * r)
  }
}
