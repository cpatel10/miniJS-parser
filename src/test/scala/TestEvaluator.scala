import edu.luc.cs.laufer.cs473.expressions._
import TestFixtures._
import org.scalatest.FunSuite
import scala.util.Success

/**
  * Created by Chingari on 4/8/2016.
  */
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

}
