package edu.luc.cs.laufer.cs473.expressions

import scala.util.parsing.combinator.JavaTokenParsers
import scala.collection.mutable
import ast._

object CombinatorParser extends JavaTokenParsers {

  /** expr ::= term { { "+" | "-" } term }* */
  def expr: Parser[Statement] =
    term ~! opt(("+" | "-") ~ term) ^^ {
      case l ~ None => l
      case l ~ Some("+" ~ r) => Plus(l, r)
      case l ~ Some("-" ~ r) => Minus(l, r)
    }

  /** term ::= factor { { "*" | "/" | "%" } factor }* */
  def term: Parser[Statement] =
    factor ~! opt(("*" | "/" | "%") ~ factor) ^^ {
      case l ~ None => l
      case l ~ Some("*" ~ r) => Times(l, r)
      case l ~ Some("/" ~ r) => Div(l, r)
      case l ~ Some("%" ~ r) => Mod(l, r)
    }

  /** factor ::= simplefactor { "." ident }* */
  def factor: Parser[Statement] = (
    simplefactor
    | simplefactor ~ "." ~ repsep(ident, ".") ^^ {
      case root ~ "." ~ selectors => Select(root, selectors.map(Variable(_)):_*)
    }
    )

  /** simplefactor ::= numericLit | "+" factor | "-" factor | "(" expr ")" | struct */
  def simplefactor: Parser[Statement] = (
    ident ^^ { case i => Variable(i)}
  | wholeNumber ^^ { case s => Constant(s.toInt) }
  | "+" ~> factor ^^ { case e => e }
  | "-" ~> factor ^^ { case e => UMinus(e) }
  | "(" ~ expr ~ ")" ^^ { case _ ~ e ~ _ => e }
  | struct ^^ {case s => s}
  )

  /** statements ::= statement* */
  def statements: Parser[List[Statement]] = rep(statement)

  /** statement   ::= expression ";" | assignment | conditional | loop | block */
  def statement: Parser[Statement] = expr <~ ";" | assignment | conditional | loop | block

  /** assignment  ::= ident { "." ident }* "=" expression ";" */
  def assignment: Parser[Statement] =
    repsep(ident, ".") ~ "=" ~ expr ~ ";" ^^ {
      case i ~ "=" ~ e ~ ";"  => Assignment(e, i.map(Variable(_)):_*)
    }

  /** conditional ::= "if" "(" expression ")" block [ "else" block ] */
  def conditional: Parser[Statement] =
    "if" ~ "(" ~> expr ~ ")" ~ block ~ opt( "else" ~ block ) ^^ {
      case  guard ~ _ ~ ifBranch ~ None => Conditional(guard, ifBranch)
      case  guard ~ _ ~ ifBranch ~ Some(_ ~ elseBranch) => Conditional(guard, ifBranch, Some(elseBranch))
    }

  /** loop ::= "while" "(" expression ")" block */
  def loop: Parser[Statement] =
    "while" ~ "(" ~> expr ~ ")" ~ block ^^ {
      case  guard ~ _ ~ body => Loop(guard, body)
    }

  /** block ::= "{" statement* "}" */
  def block: Parser[Statement] =
    "{" ~ rep(statement) ~ "}" ^^ {
      case _ ~ statements ~ _ => Block(statements:_*)
    }

  /** struct ::= "{" "}" | "{" field { "," field }* "}" */
  def struct: Parser[Statement] =
    "{" ~> repsep(field, ",") <~ "}" ^^ {
      case fields => {
        val map = fields.map(f => f.asInstanceOf[Assignment].left(0).variable -> f.asInstanceOf[Assignment].right)(collection.breakOut): Map[String, Statement]
        Struct(map)
      }
    }

  /** field ::= ident ":" expr */
  def field: Parser[Statement] =
    ident ~ ":" ~ expr ^^ {
      case i ~ ":" ~ e => Assignment(e, Variable(i))
    }

}
