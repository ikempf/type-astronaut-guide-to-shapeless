package com.ikempf._5_labelled_typeclass_derivation

import com.ikempf._5_labelled_typeclass_derivation.JsonValue.JsonObject


trait JsonObjectEncoder[A] extends JsonEncoder[A] {
  def encode(value: A): JsonObject
}

object JsonObjectEncoder {

  def apply[A](implicit enc: JsonObjectEncoder[A]): JsonObjectEncoder[A] =
    enc

}