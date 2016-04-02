package edu.luc.cs.laufer.cs473.expressions

import ast._

object behaviors {

  def evaluate(e: Expr): Int = e match {
    case Constant(c) => c
    case UMinus(r)   => -evaluate(r)
    case Plus(l, r)  => evaluate(l) + evaluate(r)
    case Minus(l, r) => evaluate(l) - evaluate(r)
    case Times(l, r) => evaluate(l) * evaluate(r)
    case Div(l, r)   => evaluate(l) / evaluate(r)
    case Mod(l, r)   => evaluate(l) % evaluate(r)
  }

  def size(e: Expr): Int = e match {
    case Constant(c) => 1
    case UMinus(r)   => 1 + size(r)
    case Plus(l, r)  => 1 + size(l) + size(r)
    case Minus(l, r) => 1 + size(l) + size(r)
    case Times(l, r) => 1 + size(l) + size(r)
    case Div(l, r)   => 1 + size(l) + size(r)
    case Mod(l, r)   => 1 + size(l) + size(r)
  }

  def depth(e: Expr): Int = e match {
    case Constant(c) => 1
    case UMinus(r)   => 1 + depth(r)
    case Plus(l, r)  => 1 + math.max(depth(l), depth(r))
    case Minus(l, r) => 1 + math.max(depth(l), depth(r))
    case Times(l, r) => 1 + math.max(depth(l), depth(r))
    case Div(l, r)   => 1 + math.max(depth(l), depth(r))
    case Mod(l, r)   => 1 + math.max(depth(l), depth(r))
  }

  def toFormattedString(prefix: String)(e: Expr): String = e match {
    case Constant(c) => prefix + c.toString
    case UMinus(r)   => buildUnaryExprString(prefix, "-", toFormattedString(prefix)(r))
    case Plus(l, r)  => buildExprString(prefix, "+", toFormattedString(prefix)(l), toFormattedString(prefix)(r))
    case Minus(l, r) => buildExprString(prefix, "-", toFormattedString(prefix)(l), toFormattedString(prefix)(r))
    case Times(l, r) => buildExprString(prefix, "*", toFormattedString(prefix)(l), toFormattedString(prefix)(r))
    case Div(l, r)   => buildExprString(prefix, "/", toFormattedString(prefix)(l), toFormattedString(prefix)(r))
    case Mod(l, r)   => buildExprString(prefix, "%", toFormattedString(prefix)(l), toFormattedString(prefix)(r))

    case Variable(i) => prefix + i
    case Assignment(l, r) => buildAssignmentString(prefix, "=", toFormattedString(prefix)(l), toFormattedString(prefix)(r))
    case Conditional(guard, ifBranch, elseBranch) => buildConditionalString(prefix, "if", guard, ifBranch, elseBranch:Option[Expr])

    case Loop(guard, body) => buildLoopString(prefix, "while", toFormattedString(prefix)(guard), toFormattedString(prefix)(body))
    case Block(expressions@_*) => buildBlockString(prefix, expressions: _*)
  }

  def toFormattedString(e: Expr): String = toFormattedString("")(e)

  def buildExprString(prefix: String, nodeString: String, leftString: String, rightString: String) = {
    val result = new StringBuilder(prefix)
    result.append("(")
    //result.append(EOL)
    result.append(leftString)
    result.append(" ")
    //result.append(EOL)
    result.append(nodeString)
    result.append(" ")
    result.append(rightString)
    result.append(")")
    result.toString
  }

  def buildUnaryExprString(prefix: String, nodeString: String, exprString: String) = {
    val result = new StringBuilder(prefix)
    result.append(nodeString)
    result.append("(")
    //result.append(EOL)
    result.append(exprString)
    result.append(")")
    result.toString
  }

  def buildAssignmentString(prefix: String, nodeString: String, leftString: String, rightString: String) = {
    val result = new StringBuilder(prefix)
    result.append(leftString)
    result.append(" ")
    result.append(nodeString)
    result.append(" ")
    result.append(rightString)
    result.append(";")
    result.append(EOL)
    result.toString
  }

  def buildConditionalString(prefix: String, nodeString: String, guard: Expr, ifBranch: Expr, elseBranch: Option[Expr]) = {
    val result = new StringBuilder(prefix).append(nodeString).append(" (")
    result.append(toFormattedString(prefix)(guard))
    result.append(") ")
    result.append(toFormattedString(prefix)(ifBranch))
    elseBranch.foreach((block: Expr) => {
      result.append("else ")
      result.append(toFormattedString(prefix)(block))
    })
    result.toString
  }

  def buildBlockString(prefix: String, expressions: Expr*) = {
    val result = new StringBuilder(prefix).append("{").append(EOL)
    result.append(expressions.map(expr => INDENT + toFormattedString(prefix)(expr)).mkString(""))
    result.append("}")
    result.toString
  }

  def buildLoopString(prefix: String, nodeString: String, guard: String, body: String) = {
    val result = new StringBuilder(prefix).append(nodeString).append(" (")
    result.append(guard)
    result.append(") ")
    result.append(body)
    result.toString
  }

  val EOL = scala.util.Properties.lineSeparator
  val INDENT = "  "
}
