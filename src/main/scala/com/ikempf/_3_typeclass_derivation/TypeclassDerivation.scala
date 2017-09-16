package com.ikempf._3_typeclass_derivation

import java.lang

import com.ikempf._3_typeclass_derivation.CsvEncoder.create
import shapeless.{:+:, ::, CNil, Coproduct, Generic, HList, HNil, Inl, Inr, Lazy}
import cats.instances.list._
import cats.syntax.applicative._

object TypeclassDerivation {

  implicit val stringEncoder = create[String](_.pure[List])
  implicit val intEncoder = create[Int](Integer.toString(_).pure[List])
  implicit val doubleEncoder = create[Double](lang.Double.toString(_).pure[List])
  implicit val booleanEncoder = create[Boolean](lang.Boolean.toString(_).pure[List])

  implicit val hnilEncoder = create[HNil](_ => Nil)
  implicit def productEncoder[H, T <: HList](implicit hEncoder: CsvEncoder[H], tEncoder: CsvEncoder[T]): CsvEncoder[H :: T] =
    create {
      case h :: t =>
        hEncoder.encode(h) ++ tEncoder.encode(t)
    }

  implicit val cnilEncoder: CsvEncoder[CNil] = create(_ => ???)
  implicit def coproductEncoder[H, T <: Coproduct](implicit hEncoder: CsvEncoder[H], tEncoder: CsvEncoder[T]): CsvEncoder[H :+: T] =
    create {
      case Inl(h)=>
        hEncoder.encode(h)
      case Inr(t) =>
        tEncoder.encode(t)
    }

  implicit def genericEncoder[A, R](implicit gen: Generic.Aux[A, R], encoder: Lazy[CsvEncoder[R]]) : CsvEncoder[A] =
    create(a => encoder.value.encode(gen.to(a)))

}