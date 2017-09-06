package com.ikempf.typeclassderivation
import java.lang

import com.ikempf.typeclassderivation.CsvEncoder.create
import shapeless.{:+:, ::, CNil, Coproduct, Generic, HList, HNil, Inl, Inr}
import cats.instances.list._
import cats.syntax.applicative._

object TypeClassDerivation {

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

  implicit def genericEncoder[A, R](implicit gen: Generic.Aux[A, R], encoder: CsvEncoder[R]) : CsvEncoder[A] =
    create(a => encoder.encode(gen.to(a)))

}