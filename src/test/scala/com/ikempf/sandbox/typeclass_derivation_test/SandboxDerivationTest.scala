package com.ikempf.sandbox.typeclass_derivation_test

import com.ikempf._3_typeclass_derivation.{IceCream, Shape}
import com.ikempf._3_typeclass_derivation.Shape.{Circle, Rectangle}
import com.ikempf.sandbox.typeclass_derivation_test.JsonValue.{JsonArray, JsonBoolean, JsonNumber, JsonObject, JsonString}
import com.ikempf.sandbox.typeclass_derivation_test.SandboxDerivation._
import org.scalatest.{FlatSpec, Matchers}
import shapeless.{:+:, CNil, Generic, HNil}

class SandboxDerivationTest extends FlatSpec with Matchers {

  "ProductEncoder" should "derive JsonEncoder typeclasses" in {
    // Given
    val iceCream = IceCream("vanilla", 2, false)

    // When
    val jsonValue = JsonEncoder[IceCream].encode(iceCream)

    // Then
    jsonValue should equal(JsonObject(List(
      "todo" -> JsonString("vanilla"),
      "todo" -> JsonNumber(2),
      "todo" -> JsonBoolean(false)
    )))
  }

  "CoProduct" should "derive JsonEncoder typeclasses" in {
    // Given
    val shapes: List[Shape] = List(
      Rectangle(3.0, 4.0),
      Circle(1.0)
    )

    // When
    val json = JsonEncoder[List[Shape]].encode(shapes)

    // Then
    json should equal(JsonArray(List(
      JsonObject(List("todo" -> JsonNumber(3), "todo" -> JsonNumber(4))),
      JsonObject(List("todo" -> JsonNumber(1))),
    )))
  }

}