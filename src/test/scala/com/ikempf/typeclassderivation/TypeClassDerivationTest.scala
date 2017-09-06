package com.ikempf.typeclassderivation

import org.scalatest.{FlatSpec, Matchers}
import TypeClassDerivation._
import com.ikempf.typeclassderivation.Shape.{Circle, Rectangle}

class TypeClassDerivationTest extends FlatSpec with Matchers {

  "Derivation" should "derive generic encoders" in {
    CsvEncoder[IceCream].encode(IceCream("vanilla", 2, true)) should equal(List("vanilla", "2", "true"))
    CsvEncoder[Employee].encode(Employee("employee", 13, false)) should equal(List("employee", "13", "false"))
    CsvEncoder[Rectangle].encode(Rectangle(15.0d, 13.3d)) should equal(List("15.0", "13.3"))
    CsvEncoder[Circle].encode(Circle(3.14d)) should equal(List("3.14"))
  }

}