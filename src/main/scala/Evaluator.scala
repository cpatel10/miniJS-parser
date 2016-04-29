package edu.luc.cs.laufer.cs473.expressions

import edu.luc.cs.laufer.cs473.expressions.ast._

import scala.collection.mutable
import scala.util.Try


trait Value[T] {
  def get: T
  def set(value:T):Value[T]
}

/** A run-time value is always a number for now. We represent NULL as 0. */
case class Num(var value:Int) extends Value[Int] {
  def get:Int=value
  def set(value:Int)= {this.value=value;this}
}

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
  val NULL = Cell(Num(0))
}

object Evaluator {

  type Store = mutable.Map[String, LValue[Value[Int]]]
  val store: Store = mutable.Map[String, LValue[Value[Int]]]()

  def evaluate(statements: List[Statement]): Try[Value[Int]] = {
    val evaluatedStatements = statements.map(evaluate(_))
    evaluatedStatements.lastOption.getOrElse(Try(Num(0)))
  }

  def evaluate(expr: Statement): Try[Value[Int]] = { Try (evaluate(store)(expr)) }

  def evaluate(store: Store)(expr: Statement): Value[Int] = expr match {
    case Constant(value) => Num(value)
    case Plus(left, right) => Num(evaluate(store)(left).get + evaluate(store)(right).get)
    case Minus(left, right) => Num(evaluate(store)(left).get - evaluate(store)(right).get)
    case Times(left, right) => Num(evaluate(store)(left).get * evaluate(store)(right).get)
    case Div(left, right) => Num(evaluate(store)(left).get / evaluate(store)(right).get)
    case Mod(left, right) => Num(evaluate(store)(left).get % evaluate(store)(right).get)

    case Variable(i) => {
      val ivalue = store.get(i)
      if (ivalue.isDefined) ivalue.get.get
      else throw new NoSuchFieldException(i)
    }

    case Assignment(right, left) => {
      val lvalue = Try(evaluate(store)(left)).getOrElse(Num(0))
      val rvalue = evaluate(store)(right)
      store(left.variable) = Cell(lvalue.set(rvalue.get))
      Cell.NULL.get

    }

    case Conditional(guard, ifBranch, elseBranch: Option[Statement]) => {
      val gvalue = evaluate(store)(guard)
      if (gvalue.get != 0) {
        evaluate(store)(ifBranch)
      } else {
        if (elseBranch.isDefined) {
          evaluate(store)(elseBranch.get)
        } else {
          Cell.NULL.get
        }
      }
    }

    case Loop(guard, body) => {
      var gvalue = evaluate(store)(guard)
      while (gvalue.get != 0) {
        evaluate(store)(body)
        gvalue = evaluate(store)(guard)
      }
      Cell.NULL.get
    }

    case Block(expressions @ _*) =>{
      expressions.foldLeft(Cell.NULL.get.asInstanceOf[Value[Int]])((c: Value[Int], s: Statement) => evaluate(store)(s))
    }
  }
}