package com.ikempf.typeclassderivation

sealed trait Tree[A]

object Tree {

  case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
  case class Leaf[A](value: A) extends Tree[A]

}