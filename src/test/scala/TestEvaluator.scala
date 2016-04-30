import edu.luc.cs.laufer.cs473.expressions._
import TestFixtures._
import org.scalatest.FunSuite
import scala.util.Success


class TestEvaluator extends FunSuite {

  test("evaluate twoAssignments") {
    val result = Evaluator.evaluate(simpleStatementList)
    assert(result === Success(Num(0)))
    assert(Evaluator.store.get("x").get === Cell(Num(5)))
    assert(Evaluator.store.get("y").get === Cell(Num(7)))
  }

  test("evaluate singleVariable") {
    val result = Evaluator.evaluate(simpleVariable)
    assert(result === Success(Num(5)))
    assert(Evaluator.store.get("x").get === Cell(Num(5)))
  }

  test("evaluate complexAssignment2") {
    val result = Evaluator.evaluate(complexAssignment2)
    val exception = intercept[java.lang.NoSuchFieldException] {
      result.get
    }
    assert(exception.getMessage === "y2")
  }

  test("evaluate simpleConditional") {
    val result = Evaluator.evaluate(simpleConditional)
    assert(result === Success(Num(0)))
    assert(Evaluator.store.get("x").get === Cell(Num(2)))
  }

  test("evaluate complexConditional") {
    val result = Evaluator.evaluate(complexConditional)
    assert(result === Success(Num(0)))
    assert(Evaluator.store.get("x").get === Cell(Num(2)))
  }
  test("evaluate BlockFixture") {
    val result = Evaluator.evaluate(blockFixture)
    val exception = intercept[java.lang.NoSuchFieldException] {
      result.get
    }
    assert(exception.getMessage === "r")
  }

  test("parser complexConditional2") {
    val result = Evaluator.evaluate(complexConditional2)
    val exception = intercept[java.lang.NoSuchFieldException] {
      result.get
    }
    assert(exception.getMessage === "r")
  }

  test("evaluate loopFixture") {
    val result = Evaluator.evaluate(loopFixture)
    val exception = intercept[java.lang.NoSuchFieldException] {
      result.get
    }
    assert(exception.getMessage === "r")
  }

  test("evaluate works on complexStructWithAssign"){
    Evaluator.evaluate(complexStructWithAssign1)
    assert(Evaluator.store.get("x").get === complexStructWithAssign1Memory)
    Evaluator.evaluate(complexStructWithAssign2)
    assert(Evaluator.store.get("x").get === complexStructWithAssign2Memory)
    Evaluator.evaluate(complexStructWithAssign3)
    assert(Evaluator.store.get("x").get === complexStructWithAssign3Memory)
  }

  test("evaluate works on AssignNumAsIns") {
    val result = Evaluator.evaluate(AssignNumAsIns)
    val thrown = intercept[java.lang.NoSuchFieldException] {
      result.get
    }
    assert(thrown.getMessage === "b")
  }

}
