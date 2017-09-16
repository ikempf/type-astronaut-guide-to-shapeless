package com.ikempf.sandbox.typeclass_derivation_test

import com.ikempf.sandbox.typeclass_derivation_test.JsonValue.{JsonArray, JsonBoolean, JsonNull, JsonNumber, JsonObject, JsonString}
import shapeless.{:+:, ::, CNil, Coproduct, Generic, HList, HNil, Inl, Inr, Lazy}

object SandboxDerivation {

  implicit val stringEncoder: JsonEncoder[String] =
    JsonString.apply

  implicit val doubleEncoder: JsonEncoder[Double] =
    JsonNumber.apply

  implicit val intEncoder: JsonEncoder[Int] =
    i => JsonNumber(i)

  implicit val booleanEncoder: JsonEncoder[Boolean] =
    JsonBoolean.apply

  implicit def listEncoder[A: JsonEncoder]: JsonEncoder[List[A]] =
    list => JsonArray(list.map(JsonEncoder[A].encode))

  implicit def optionEncoder[A: JsonEncoder]: JsonEncoder[Option[A]] =
    opt => opt.map(JsonEncoder[A].encode).getOrElse(JsonNull)

  // !!! Can't use SAM style; Scala bug ??
  implicit val hNilEncoder: JsonObjectEncoder[HNil] =
    new JsonObjectEncoder[HNil] {
      override def encode(value: HNil) = JsonObject(List.empty)
    }

  implicit def productEncoder[H: JsonEncoder, T <: HList : JsonObjectEncoder]: JsonObjectEncoder[H :: T] =
    new JsonObjectEncoder[::[H, T]] {
      override def encode(value: ::[H, T]) = {
        value match {
          case h :: t =>
            val namedHead = "todo" -> JsonEncoder[H].encode(h)
            val tailFields = JsonObjectEncoder[T].encode(t).fields

            JsonObject(List(namedHead) ++ tailFields)
        }
      }
    }

  implicit val cNilEncoder: JsonEncoder[CNil] =
    new JsonEncoder[CNil] {
      override def encode(value: CNil): JsonObject = JsonObject(List.empty)
    }

  implicit def coproductEncoder[H: JsonEncoder, T <: Coproduct: JsonEncoder]: JsonEncoder[H :+: T] =
    new JsonEncoder[H :+: T] {
      override def encode(value: H :+: T): JsonValue =
        value match {
          case Inl(h) =>
            JsonEncoder[H].encode(h)
          case Inr(t) =>
            JsonEncoder[T].encode(t)
        }
    }

  implicit def genericEncoder[A, R](implicit gen: Generic.Aux[A, R], encoder: Lazy[JsonEncoder[R]]): JsonEncoder[A] =
    a => encoder.value.encode(gen.to(a))

}