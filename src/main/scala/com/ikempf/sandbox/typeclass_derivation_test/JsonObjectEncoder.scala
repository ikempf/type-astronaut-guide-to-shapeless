package com.ikempf.sandbox.typeclass_derivation_test

import com.ikempf.sandbox.typeclass_derivation_test.JsonValue.JsonObject

trait JsonObjectEncoder[A] extends JsonEncoder[A] {
  def encode(value: A): JsonObject
}

object JsonObjectEncoder {

  def apply[A](implicit enc: JsonObjectEncoder[A]): JsonObjectEncoder[A] =
    enc

}