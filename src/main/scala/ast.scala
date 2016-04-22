package edu.luc.cs.laufer.cs473.expressions.ast

/** An initial algebra of arithmetic expressions. */
sealed trait Statement
case class Constant(value: Int) extends Statement
case class UMinus(expr: Statement) extends Statement
case class Plus(left: Statement, right: Statement) extends Statement
case class Minus(left: Statement, right: Statement) extends Statement
case class Times(left: Statement, right: Statement) extends Statement
case class Div(left: Statement, right: Statement) extends Statement
case class Mod(left: Statement, right: Statement) extends Statement

case class Variable(variable:String) extends Statement
case class Assignment(left:Variable, right:Statement) extends Statement
case class Conditional(guard:Statement, ifBranch:Statement, elseBranch:Option[Statement]=None) extends Statement
case class Loop(guard:Statement, body:Statement) extends Statement
case class Block(expressions:Statement*) extends Statement
case class Struct(struct:Map[String, Statement])extends Statement
case class Select(root: Statement, selectors:Variable*) extends Statement