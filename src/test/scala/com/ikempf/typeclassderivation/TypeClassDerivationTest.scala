package com.ikempf.typeclassderivation

import org.scalatest.{FlatSpec, Matchers}
import TypeClassDerivation._
import com.ikempf.typeclassderivation.Shape.{Circle, Rectangle}
import com.ikempf.typeclassderivation.Tree.{Branch, Leaf}

class TypeClassDerivationTest extends FlatSpec with Matchers {

  "Product derivation" should "derive generic encoders" in {
    CsvEncoder[IceCream].encode(IceCream("vanilla", 2, true)) should equal(List("vanilla", "2", "true"))
    CsvEncoder[Employee].encode(Employee("employee", 13, false)) should equal(List("employee", "13", "false"))
    CsvEncoder[Rectangle].encode(Rectangle(15.0d, 13.3d)) should equal(List("15.0", "13.3"))
    CsvEncoder[Circle].encode(Circle(3.14d)) should equal(List("3.14"))
  }

  "Coproduct derivation" should "derive generic encorders" in {
    // Given
    val shapes: List[Shape] = List(
      Rectangle(3.0, 4.0),
      Circle(1.0)
    )

    // When
    val csvLines = shapes.map(CsvEncoder[Shape].encode)

    // Then
    csvLines should equal(List(List("3.0", "4.0"), List("1.0")))
  }

  "Recursive derivation" should "derive generic encoders" in {
    // Given
    val tree = Branch(Leaf(5), Leaf(3))

    // When
    val line = CsvEncoder[Tree[Int]].encode(tree)

    // Then
    line should equal(List("5", "3"))
  }

}