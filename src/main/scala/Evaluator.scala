package edu.luc.cs.laufer.cs473.expressions

import edu.luc.cs.laufer.cs473.expressions.ast._

import scala.collection.mutable
import scala.util.Try

/** Something that can be used on the right-hand side of an assignment. */
trait RValue[T] {
  def get: T
}

/** Something that can be used on the left-hand side of an assignment. */
trait LValue[T] extends RValue[T] {
  def set(value: T): LValue[T]
}

/** A cell for storing a value. */
case class Cell[T](var value: T) extends LValue[T] {
  override def get = value
  override def set(value: T) = { this.value = value; this }
}

/** A companion object defining a useful Cell instance. */
object Cell {
  val NULL = Cell(0)
}

object Evaluator {

  type Store = mutable.Map[String, LValue[Int]]


  def evaluate(store: Store)(expr: Expr): LValue[Int] = expr match {
    case Constant(value) => Cell(value)
    case Plus(left, right) => Cell(evaluate(store)(left).get + evaluate(store)(right).get)
    case Minus(left, right) => Cell(evaluate(store)(left).get - evaluate(store)(right).get)
    case Times(left, right) => Cell(evaluate(store)(left).get * evaluate(store)(right).get)
    case Div(left, right) => Cell(evaluate(store)(left).get / evaluate(store)(right).get)
    case Mod(left, right) => Cell(evaluate(store)(left).get % evaluate(store)(right).get)

    case Variable(i) => {
      val ivalue = store.get(i)
      if (ivalue.isDefined) Cell(ivalue.get.get)
      else Cell(0)
    }

    case Assignment(left, right) => left match {
      case Variable(i) => {
        val rvalue = evaluate(store)(right)
        val lvalue = evaluate(store)(left)
        store(i) = lvalue.set(rvalue.get)
        store(i)
      }
    }

    case Conditional(guard, ifBranch, elseBranch: Option[Expr]) => {
      val gvalue = evaluate(store)(guard)
      if (gvalue.get != 0) {
        evaluate(store)(ifBranch)
      }
//      else {
//
//
//      }
      Cell.NULL
    }

    case Loop(guard, body) => {
      var gvalue = evaluate(store)(guard)
      while (gvalue.get != 0) {
        evaluate(store)(body)
        gvalue = evaluate(store)(guard)
      }
      Cell.NULL
    }

    case Block(exprs @ _*) =>{
       exprs.foldLeft(Cell.NULL.asInstanceOf[LValue[Int]])((c: LValue[Int], s: Expr) => evaluate(store)(s))
   }
  }
}


