import org.scalamock.scalatest.MockFactory
import org.scalatest.funsuite.AnyFunSuite

class PointTest extends AnyFunSuite with MockFactory{
  test("Point(20, 20) should be raise IllegalArgumentException") {
    intercept[IllegalArgumentException] {
      Point(20, 20)
    }
  }
//  test("when call by new, verify is skipped") {
//    new Point(20, 20)
//  }
  test("test mock") {
    val dummyVerifier = (_:Int, _:Int, _:Int) => {}
    class mockablePoint extends Point(100, 100, dummyVerifier)
    mock[mockablePoint]
  }
}

