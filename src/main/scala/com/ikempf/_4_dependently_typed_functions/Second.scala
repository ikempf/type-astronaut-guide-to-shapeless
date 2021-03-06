package com.ikempf._4_2_dependently_typed_functions

import shapeless.HList
import shapeless.::

trait Second[L <: HList] {
  type Out
  def apply(value: L): Out
}

object Second {

  type Aux[L <: HList, O] = Second[L] {type Out = O}

  def apply[L <: HList](implicit inst: Second[L]): Aux[L, inst.Out] =
    inst

  implicit def hlistSecond[A, B, Rest <: HList]: Aux[A :: B :: Rest, B] =
    new Second[A :: B :: Rest] {
      type Out = B

      override def apply(value: A :: B :: Rest): B = value.tail.head
    }

}