package edu.luc.cs.laufer.cs473.expressions

import _root_.jline.console.ConsoleReader

object CombinatorCalculator extends App {

  def processExpr(input: String): Unit = {
    println("You entered: " + input)
    val result = CombinatorParser.parseAll(CombinatorParser.statements, input)
    if (result.isEmpty) {
      println("This expression could not be parsed")
    }
    else {
      import behaviors._
      val expr = result.get
      println("The parsed statements are: ")
      println(expr)
      println("The unparsed statements are: ")
      println(toFormattedString(expr))
      print("It evaluates to ")
      val eval = Evaluator.evaluate(expr)
      println(eval)
      println("Memory: " + Evaluator.store)
    }
  }

  val console = new ConsoleReader()
  console.setPrompt("minijs> ")
  println("Enter the statements and press <enter> to parse your input or <quit> to quit:")
  println("Memory: " + Evaluator.store)


  if (args.length > 0) {
    processExpr(args mkString " ")
  }
  else {
    Iterator.continually(console.readLine()).takeWhile(_ != "quit").foreach(processExpr(_))
  }
}
