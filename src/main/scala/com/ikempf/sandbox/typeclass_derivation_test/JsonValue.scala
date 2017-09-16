package com.ikempf.sandbox.typeclass_derivation_test

sealed trait JsonValue

object JsonValue {

  case object JsonNull extends JsonValue
  case class JsonBoolean(value: Boolean) extends JsonValue
  case class JsonNumber(value: Double) extends JsonValue
  case class JsonString(value: String) extends JsonValue
  case class JsonArray(items: List[JsonValue]) extends JsonValue
  case class JsonObject(fields: List[(String, JsonValue)]) extends JsonValue

}