package com.ikempf.sandbox.typeclass_derivation_test

trait JsonEncoder[A] {
  def encode(value: A): JsonValue
}

object JsonEncoder {

  def apply[A](implicit enc: JsonEncoder[A]): JsonEncoder[A] =
    enc

}