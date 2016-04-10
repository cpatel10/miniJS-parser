package edu.luc.cs.laufer.cs473.expressions

import org.parboiled2._
import ast._

class PegParser(val input: ParserInput) extends Parser {

  def InputLine = rule { WhiteSpace ~ Expression ~ EOI }

  /** expression ::= term { { "+" | "-" } term }* */
  def Expression = rule {
    Term ~ zeroOrMore(
      ws('+') ~ Term ~> (Plus(_: Statement, _))
    | ws('-') ~ Term ~> (Minus(_: Statement, _))
    )
  }

  /** term ::= factor { { "*" | "/" | "%" } factor }* */
  def Term = rule {
    Factor ~ zeroOrMore(
      ws('*') ~ Factor ~> (Times(_: Statement, _))
    | ws('/') ~ Factor ~> (Div(_: Statement, _))
    | ws('%') ~ Factor ~> (Mod(_: Statement, _))
    )
  }

  /** factor ::= number | "+" factor | "-" factor | "(" expression ")" */
  def Factor: Rule1[Statement] = rule { Number | UnaryPlus | UnaryMinus | Parens }

  // explicitly handle trailing whitespace
  def Number = rule { capture(Digits) ~ WhiteSpace ~> ((s: String) => Constant(s.toInt)) }

  def UnaryPlus = rule { ws('+') ~ Factor }

  def UnaryMinus = rule { ws('-') ~ Factor ~> (UMinus(_: Statement)) }

  def Parens = rule { ws('(') ~ Expression ~ ws(')') }

  def Digits = rule { oneOrMore(CharPredicate.Digit) }

  val WhiteSpaceChar = CharPredicate(" \n\r\t\f")

  def WhiteSpace = rule { zeroOrMore(WhiteSpaceChar) }

  def ws(c: Char) = rule { c ~ WhiteSpace }
}
