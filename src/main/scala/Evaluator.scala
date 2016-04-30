package edu.luc.cs.laufer.cs473.expressions

import edu.luc.cs.laufer.cs473.expressions.Evaluator.Store
import edu.luc.cs.laufer.cs473.expressions.ast._

import scala.collection.mutable
import scala.util.{Failure, Success, Try}


trait Value

/** A run-time value is always a number for now. We represent NULL as 0. */
case class Num(var value:Int) extends Value {
  def get:Int=value
  def set(value:Int)= {this.value=value;this}
}
case class Ins(var value: Store) extends Value{
  def get:Store=value
  def set(value:Store)={this.value=value;this}
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
  val NULL = Cell(Num(0)).asInstanceOf[Cell[Value]]
}

object Evaluator {

  type Store = mutable.Map[String, Cell[Value]]
  val store: Store = mutable.Map[String, Cell[Value]]()

  /**
    * An object (instance) is the same as a memory store.
    */
  type Instance = Store

  /**
    * A run-time value is either a number or an object.
    */
  //type Result = Either[Cell[Value[Int]], Cell[Value[Instance]]]

  def evaluate(statements: List[Statement]): Try[Value] = {
    val evaluatedStatements = statements.map(evaluate(_))
    evaluatedStatements.lastOption.getOrElse(Try(Num(0)))
  }

  def evaluate(expr: Statement): Try[Value] = { Try (evaluate(store)(expr).get) }

  def evaluate(store: Store)(expr: Statement): Cell[Value] = expr match {
    case Constant(value) => Cell(Num(value))
    case UMinus(value) => Cell(Num(-evaluate(store)(value).get.asInstanceOf[Num].get))
    case Plus(left, right) => Cell(Num(evaluate(store)(left).get.asInstanceOf[Num].get + evaluate(store)(right).get.asInstanceOf[Num].get))
    case Minus(left, right) => Cell(Num(evaluate(store)(left).get.asInstanceOf[Num].get - evaluate(store)(right).get.asInstanceOf[Num].get))
    case Times(left, right) => Cell(Num(evaluate(store)(left).get.asInstanceOf[Num].get * evaluate(store)(right).get.asInstanceOf[Num].get))
    case Div(left, right) => Cell(Num(evaluate(store)(left).get.asInstanceOf[Num].get / evaluate(store)(right).get.asInstanceOf[Num].get))
    case Mod(left, right) => Cell(Num(evaluate(store)(left).get.asInstanceOf[Num].get % evaluate(store)(right).get.asInstanceOf[Num].get))

    case Variable(i) => {
      val ivalue = store.get(i)
      if (ivalue.isDefined) ivalue.get
      else throw new NoSuchFieldException(i)
    }

    case Select(root, selectors @_*) => {
      selectors.foldLeft(evaluate(store)(root))((cell: Cell[Value], selector: Variable) =>
      cell.get match {
        case Ins(obj) => Try(obj(selector.variable)).getOrElse(throw new NoSuchFieldException(selector.variable))
        case Num(i) => throw new NoSuchFieldException(selector.variable)
      })
    }

    case Assignment(right, left @_*) => {
      val rvalue = evaluate(store)(right)
      Try(evaluate(store)(Select(left.head, left.tail.dropRight(1):_*))) match {
        case Success(cell) =>
          if (left.tail.isEmpty) cell.set(rvalue.get)
          else cell.get match {
            case Ins(obj) => obj(left.last.variable) = rvalue
            case Num(i) => throw new NoSuchFieldException(left.last.variable)
          }
        case Failure(exception: NoSuchFieldException) =>
          if (left.tail.isEmpty) store(left.head.variable) = Cell(rvalue.get)
          else throw exception
        case Failure(exception) => throw exception
      }
      Cell.NULL

    }

    case Struct(map) => {
      map.foldLeft(Cell(Ins(mutable.Map[String, Cell[Value]]())))((cell, pair) => {
        cell.get.value(pair._1) = evaluate(cell.get.value)(pair._2)
        cell
      }).asInstanceOf[Cell[Value]]
    }

    case Conditional(guard, ifBranch, elseBranch: Option[Statement]) => {
      val gvalue = evaluate(store)(guard)
      if (gvalue.get != 0) {
        evaluate(store)(ifBranch)
      } else {
        if (elseBranch.isDefined) {
          evaluate(store)(elseBranch.get)
        } else {
          Cell.NULL
        }
      }
    }

    case Loop(guard, body) => {
      var gvalue = evaluate(store)(guard)
      while (gvalue.get != 0) {
        evaluate(store)(body)
        gvalue = evaluate(store)(guard)
      }
      Cell.NULL
    }

    case Block(expressions @ _*) =>{
      expressions.foldLeft(Cell.NULL)((c: Cell[Value], s: Statement) => evaluate(store)(s))
    }
  }
}