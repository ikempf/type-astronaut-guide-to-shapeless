package com.ikempf._3_typeclass_derivation

sealed trait Shape

object Shape {

  final case class Rectangle(width: Double, height: Double) extends Shape
  final case class Circle(radius: Double) extends Shape

}