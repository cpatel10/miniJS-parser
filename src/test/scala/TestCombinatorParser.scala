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
  val parsedExpr9 = CombinatorParser.parseAll(CombinatorParser.statement, loopString1)
  val parsedExpr10 = CombinatorParser.parseAll(CombinatorParser.statement, loopString2)
  val parsedExpr11 = CombinatorParser.parseAll(CombinatorParser.statements, simpleStatementListString)
  val parsedExpr12 = CombinatorParser.parseAll(CombinatorParser.statement, complexConditionalString)
  val parsedExpr13 = CombinatorParser.parseAll(CombinatorParser.statement, complexConditional2String)
  val parsedExpr14= CombinatorParser.parseAll(CombinatorParser.statement, emptyStructAssignmentString)
  val parsedExpr15 = CombinatorParser.parseAll(CombinatorParser.statement, simpleStructAssignmentString)
  val parsedExpr16 = CombinatorParser.parseAll(CombinatorParser.statement, complexStructAssignmentString)
  val parsedExpr17 = CombinatorParser.parseAll(CombinatorParser.statement, nestedStructAssignmentString)
  val parsedExpr18 = CombinatorParser.parseAll(CombinatorParser.statement, simpleSelectStr)
  val parsedExpr19 = CombinatorParser.parseAll(CombinatorParser.statement, selectWithStructStr)
  val parsedExpr20 = CombinatorParser.parseAll(CombinatorParser.statement, complexSelectStr)

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
  test("parser works: emptyStructAssignment"){assert(parsedExpr14.get===emptyStructAssignment)}
  test("parser works: simpleStructAssignment"){assert(parsedExpr15.get===simpleStructAssignment)}
  test("parser works: complexStructAssignment"){assert(parsedExpr16.get===complexStructAssignment)}
  test("parser works: nestedStructAssignment"){assert(parsedExpr17.get===nestedStructAssignment)}
  test("parser works: simpleSelect"){assert(parsedExpr18.get===simpleSelect)}
  test("parser works: selectWithStruct"){assert(parsedExpr19.get===selectWithStruct)}
  test("parser works: complexSelect"){assert(parsedExpr20.get===complexSelect)}

}
