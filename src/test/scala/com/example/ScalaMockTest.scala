package com.example

import com.example.Greetings.Formatter
import org.scalamock.scalatest.MockFactory
import org.scalatest.exceptions.TestFailedException
import org.scalatest.funsuite.AnyFunSuite

class ScalaMockTest extends AnyFunSuite with MockFactory{
  test("sayHello") {
    val mockFormatter = mock[Formatter]

    (mockFormatter.format _).expects("Mr Bond").returning("Ah, Mr Bond. I've been expecting you").once()

    Greetings.sayHello("Mr Bond", mockFormatter)
  }

  test("MockitoStyle") {
    val mockFormatter = stub[Formatter]
    val bond = "Mr Bond"

    (mockFormatter.format _).when(bond).returns("Ah, Mr Bond. I've been expecting you")

    Greetings.sayHello(bond, mockFormatter)

    (mockFormatter.format _).verify(bond).once()
  }

  test("WithBrokenGreeter") {
    val brokenFormatter = mock[Formatter]

    (brokenFormatter.format _).expects(*).throwing(new NullPointerException).anyNumberOfTimes()

    intercept[NullPointerException] {
      Greetings.sayHello("Erza", brokenFormatter)
    }
  }

  test("WithVariableParameters") {
    val australianFormat = mock[Formatter]

    (australianFormat.format _).expects(*).onCall { s: String => s"G'day $s" }.twice()

    Greetings.sayHello("Wendy", australianFormat)
    Greetings.sayHello("Gray", australianFormat)
  }

  test("WithParamAssertion") {
    val teamNatsu = Set("Natsu", "Lucy", "Happy", "Erza", "Gray", "Wendy", "Carla")
    val formatter = mock[Formatter]

    def assertTeamNatsu(s: String): Unit = {
      assert(teamNatsu.contains(s))
    }

    // argAssert fails early
    (formatter.format _).expects(argAssert(assertTeamNatsu _)).onCall { s: String => s"Yo $s" }.anyNumberOfTimes()

    // 'where' verifies at the end of the test
    (formatter.format _).expects(where { s: String => teamNatsu contains s }).onCall { s: String => s"Yo $s" }.anyNumberOfTimes()

    Greetings.sayHello("Carla", formatter)
    Greetings.sayHello("Happy", formatter)
    Greetings.sayHello("Lucy", formatter)
    intercept[TestFailedException] {
      Greetings.sayHello("Jack", formatter)
    }
  }
}

object Greetings {
  trait Formatter { def format(s: String): String }
  object EnglishFormatter { def format(s: String): String = s"Hello $s" }
  object GermanFormatter { def format(s: String): String = s"Hallo $s" }
  object JapaneseFormatter { def format(s: String): String =  s"こんにちは $s" }

  def sayHello(name: String, formatter: Formatter): Unit = {
    println(formatter.format(name))
  }
}
