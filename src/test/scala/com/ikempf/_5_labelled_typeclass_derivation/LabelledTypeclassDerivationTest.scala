package com.ikempf._5_labelled_typeclass_derivation

import com.ikempf._3_typeclass_derivation.{IceCream, Shape}
import org.scalatest.{FlatSpec, Matchers}
import LabelledTypeclassDerivation._
import com.ikempf._3_typeclass_derivation.Shape.{Circle, Rectangle}
import com.ikempf._5_labelled_typeclass_derivation.JsonValue.{JsonArray, JsonBoolean, JsonNumber, JsonObject, JsonString}

class LabelledTypeclassDerivationTest extends FlatSpec with Matchers {

  "ProductEncoder" should "derive JsonEncoder typeclasses" in {
    // Given
    val iceCream = IceCream("vanilla", 2, false)

    // When
    val jsonValue = JsonEncoder[IceCream].encode(iceCream)

    // Then
    jsonValue should equal(JsonObject(List(
      "name" -> JsonString("vanilla"),
      "numCherries" -> JsonNumber(2),
      "inCone" -> JsonBoolean(false)
    )))
  }

  "CoproductEncoder" should "derive JsonEncoder typeclasses" in {
    // Given
    val shapes: List[Shape] = List(
      Rectangle(3.0, 4.0),
      Circle(1.0)
    )

    // When
    val json = JsonEncoder[List[Shape]].encode(shapes)

    // Then
    json should equal(JsonArray(List(
      JsonObject(List("width" -> JsonNumber(3), "height" -> JsonNumber(4))),
      JsonObject(List("radius" -> JsonNumber(1))),
    )))
  }

}