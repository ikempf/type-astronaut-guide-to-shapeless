package com.ikempf._5_labelled_typeclass_derivation

trait JsonEncoder[A] {
  def encode(value: A): JsonValue
}

object JsonEncoder {

  def apply[A](implicit enc: JsonEncoder[A]): JsonEncoder[A] =
    enc

}