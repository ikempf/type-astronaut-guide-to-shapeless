package com.ikempf._4_2_dependently_typed_functions

import org.scalatest.{FlatSpec, Matchers}
import shapeless.{::, HNil}


class SecondTest extends FlatSpec with Matchers {

  "Second" should "retrieve second element from list" in {
    // Given
    val l = 5 :: "str" :: true :: HNil

    // When
    val second = Second[Int :: String :: Boolean :: HNil]

    // Then
    second(l) should equal("str")
  }

  it should "fail for a one element list" in {
    "Second[String :: HNil]" shouldNot compile
  }

}