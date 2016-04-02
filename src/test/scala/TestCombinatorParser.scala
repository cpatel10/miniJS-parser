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
  val parsedExpr7 = CombinatorParser.parseAll(CombinatorParser.conditional, simpleConditionalString)
  val parsedExpr8 = CombinatorParser.parseAll(CombinatorParser.statement, blockString)
  val parsedExpr9 = CombinatorParser.parseAll(CombinatorParser.loop, loopString1)
  val parsedExpr10 = CombinatorParser.parseAll(CombinatorParser.loop, loopString2)
  val parsedExpr11 = CombinatorParser.parseAll(CombinatorParser.statements, simpleStatementListString)
  val parsedExpr12 = CombinatorParser.parseAll(CombinatorParser.statement, complexConditionalString)
  val parsedExpr13 = CombinatorParser.parseAll(CombinatorParser.statement, complexConditional2String)


  test("parser works: complex1") { assert(parsedExpr.get === complex1) }
  test("parser works: complex2") { assert(parsedExpr2.get === complex1) }
  test("parser works: simpleVariable") { assert(parsedExpr3.get === simpleVariable)}
  test("parser works: simpleAssignment") { assert(parsedExpr4.get === simpleAssignment)}
  test("parser works: complexExpression") { assert(parsedExpr5.get === complexExpression)}
  test("parser works: complexAssignment2") { assert(parsedExpr6.get === complexAssignment2)}
  test("parser works: simpleConditional") { assert(parsedExpr7.get === simpleConditional)}
  test("parser works: block") { assert(parsedExpr8.get === blockFixture)}
  test("parser works: loop1") { assert(parsedExpr9.get === loopFixture)}
  test("parser works: loop2") { assert(parsedExpr10.get === loopFixture)}
  test("parser works: simpleStatementList") { assert(parsedExpr11.get === simpleStatementList)}
  test("parser works: complexConditional") { assert(parsedExpr12.get === complexConditional)}
  test("parser works: complexConditional2") { assert(parsedExpr13.get === complexConditional2)}

}
