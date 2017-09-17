package com.ikempf._5_labelled_typeclass_derivation

import com.ikempf._5_labelled_typeclass_derivation.JsonValue.{JsonArray, JsonBoolean, JsonNull, JsonNumber, JsonObject, JsonString}
import shapeless.labelled.FieldType
import shapeless.{:+:, ::, CNil, Coproduct, HList, HNil, Inl, Inr, LabelledGeneric, Lazy, Witness}

object LabelledTypeclassDerivation {

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

  implicit val hNilEncoder: JsonObjectEncoder[HNil] =
    _ => JsonObject(List.empty)

  implicit def productEncoder[K <: Symbol : Witness.Aux, H: JsonEncoder, T <: HList : JsonObjectEncoder]: JsonObjectEncoder[FieldType[K, H] :: T] =
    new JsonObjectEncoder[FieldType[K, H] :: T] {
      override def encode(value: FieldType[K, H] :: T): JsonObject = {
        value match {
          case h :: t =>
            val name: String = implicitly[Witness.Aux[K]].value.name
            val namedHead = name -> JsonEncoder[H].encode(h)
            val tailFields = JsonObjectEncoder[T].encode(t).fields

            JsonObject(List(namedHead) ++ tailFields)
        }
      }
    }

  implicit val cNilEncoder: JsonEncoder[CNil] =
    _ => ???

  implicit def coproductEncoder[K <: Symbol : Witness.Aux, H: JsonEncoder, T <: Coproduct : JsonEncoder]: JsonEncoder[FieldType[K, H] :+: T] =
    new JsonEncoder[FieldType[K, H] :+: T] {
      override def encode(value: FieldType[K, H] :+: T): JsonValue =
        value match {
          case Inl(h) =>
            JsonEncoder[H].encode(h)
          case Inr(t) =>
            JsonEncoder[T].encode(t)
        }
    }

  implicit def genericEncoder[A, R](implicit gen: LabelledGeneric.Aux[A, R], encoder: Lazy[JsonEncoder[R]]): JsonEncoder[A] = {
    a => encoder.value.encode(gen.to(a))
  }

}