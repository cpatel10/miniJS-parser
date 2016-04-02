package edu.luc.cs.laufer.cs473.expressions

import org.scalatest.FunSuite

import TestFixtures._

object MainCombinatorParser extends App {
  val parsedExpr = CombinatorParser.parseAll(CombinatorParser.expr, complex1string)
  println(parsedExpr.get)
  println(complex1)
  println(parsedExpr.get == complex1)
  println(behaviors.evaluate(parsedExpr.get))
}

class TestCombinatorParser extends FunSuite {
  val parsedExpr = CombinatorParser.parseAll(CombinatorParser.expr, complex1string)
  val parsedExpr2 = CombinatorParser.parseAll(CombinatorParser.expr, complex1string2)
  val parsedExpr3 = CombinatorParser.parseAll(CombinatorParser.expr, simpleVariableString)
  val parsedExpr4 = CombinatorParser.parseAll(CombinatorParser.statement, simpleAssignmentString)
  val parsedExpr5 = CombinatorParser.parseAll(CombinatorParser.statement, complexExpressionString)
  val parsedExpr6 = CombinatorParser.parseAll(CombinatorParser.statement, complexAssignment2String)
  val parsedExpr7 = CombinatorParser.parseAll(CombinatorParser.statement, simpleConditionalString)
  val parsedExpr8 = CombinatorParser.parseAll(CombinatorParser.statement, blockString)

  test("parser works 1") { assert(parsedExpr.get === complex1) }
  test("parser works 2") { assert(parsedExpr2.get === complex1) }
  test("parser works 3") { assert(parsedExpr3.get === simpleVariable)}
  test("parser works 4") { assert(parsedExpr4.get === simpleAssignment)}
  test("parser works 5") { assert(parsedExpr5.get === complexExpression)}
  test("parser works 6") { assert(parsedExpr6.get === complexAssignment2)}
  test("parser works 7") { assert(parsedExpr7.get === simpleConditional)}
  test("parser works 8") { assert(parsedExpr8.get === block)}

}
