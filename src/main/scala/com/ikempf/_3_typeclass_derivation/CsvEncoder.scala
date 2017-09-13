package com.ikempf._3_typeclass_derivation

trait CsvEncoder[A] {
  def encode(value: A): List[String]
}

object CsvEncoder {

  def apply[A](implicit encoder: CsvEncoder[A]): CsvEncoder[A] =
    encoder

  def create[A](f: A => List[String]) = new CsvEncoder[A] {
    override def encode(value: A) = f(value)
  }

}