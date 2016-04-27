package edu.luc.cs.laufer.cs473.expressions

import scala.collection.mutable;


object TestFixtures {

  import ast._

  val complex1 =
    Div(
      Minus(
        Plus(
          Constant(1),
          Constant(2)
        ),
        Times(
          Constant(3),
          Constant(4)
        )
      ),
      Constant(5)
    );

  val complex1string = "((1 + 2) - (3 * 4)) / 5"

  val complex1string2 = "  ((1 + 2) - (3 * 4)) / 5  "

  val complex2 =
    Mod(
      Minus(
        Plus(
          Constant(1),
          Constant(2)
        ),
        Times(
          UMinus(
            Constant(3)
          ),
          Constant(4)
        )
      ),
      Constant(5)
    );

  val simpleVariableString = "x"

  val simpleVariable = Variable("x")

  val simpleAssignmentString = "x = y;"

  val simpleAssignment = Assignment(
    Variable("y"),
    Variable("x")
    //Constant(5)
  );

  val simpleStatementListString = "x = 5 ; y = 7;"

  val simpleStatementList =
    List(
      Assignment(
        Constant(5),
        Variable("x")
      ),
      Assignment(
        Constant(7),
        Variable("y")
      )
    )

  val complexExpressionString = "((1 + y2) - (3 * y4)) / 5;"

  val complexExpression =
    Div(
      Minus(
        Plus(
          Constant(1),
          Variable("y2")
        ),
        Times(
          Constant(3),
          Variable("y4")
        )
      ),
      Constant(5)
    )

  val complexAssignment2String = "x = ((1 + y2) - (3 * y4)) / 5;"

  val complexAssignment2 =
    Assignment(
      Div(
        Minus(
          Plus(
            Constant(1),
            Variable("y2")
          ),
          Times(
            Constant(3),
            Variable("y4")
          )
        ),
        Constant(5)
      ),
      Variable("x")
    )

  val simpleConditionalString = "if (1) { x = 2; }"

  val simpleConditional =
    Conditional(
      Constant(1),
      Block(
        Assignment(
          Constant(2),
          Variable("x")
        )
      )
    )

  val complexConditionalString = "if (1) { x = 2; } else { x = 3; }"

  val complexConditional =
    Conditional(
      Constant(1),
      Block(
        Assignment(
          Constant(2),
          Variable("x")
        )
      ),
      Some(Block(
        Assignment(
          Constant(3),
          Variable("x")

        )
      ))
    )

  val blockString = "{ r = r + x; y = y + 1 ; }"

  val blockFixture =
    Block(
      Assignment(
        Plus(
          Variable("r"),
          Variable("x")
        ),
        Variable("r")
      ),
      Assignment(
        Plus(
          Variable("y"),
          Constant(1)
        ),
        Variable("y")
      )
    )

  val complexConditional2String = "if (4) { r = r + x; y = y + 1; }"

  val complexConditional2 =
    Conditional(
      Constant(4),
      Block(
        Assignment(
          Plus(
            Variable("r"),
            Variable("x")
          ),
          Variable("r")
        ),
        Assignment(
          Plus(
            Variable("y"),
            Constant(1)
          ),
          Variable("y")
        )
      )
    )

  val loopString1 = "while (y) { r = r + x;  y = y - 1; }"

  val loopString2 = "while (y) {\n  r = r + x \n; y = y - 1 ;\n}"

  val loopFixture =
    Loop(
      Variable("y"),
      Block(
        Assignment(
          Plus(
            Variable("r"),
            Variable("x")
          ),
          Variable("r")
        ),
        Assignment(
          Minus(
            Variable("y"),
            Constant(1)
          ),
          Variable("y")
        )
      )
    )

   val simpleWhileString= "x = 2; y = 3; r = 0; while (y) { r = r + x ; y = y - 1; }"


  val simpleWhile =
    Block(
      Assignment(
        Constant(2),
        Variable("x")


      ),
      Assignment(
        Constant(3),
        Variable("y")

      ),
      Assignment(
        Constant(0),
        Variable("r")

      ),
      Loop(
        Variable("y"),
        Block(
          Assignment(
            Plus(
              Variable("r"),
              Variable("x")
            ),
            Variable("r")
          ),
          Assignment(
            Minus(
              Variable("y"),
              Constant(1)
            ),
            Variable("y")
          )
        )
      ))


  val emptyStructAssignmentString=  "x={};"

  val emptyStructAssignment =
        Assignment(
        Struct(Map[String, Statement]()),
        Variable("x")
      )


  val simpleStructAssignmentString = "x={a:4};"

  val simpleStructAssignment =

      Assignment(
        Struct(
          Map[String, Statement]("a" -> Constant(4))
        ),
        Variable("x")
      )


  val complexStructAssignmentString = "x = {a: {b: 4}, c: 9};"

  val complexStructAssignment=

      Assignment(
        Struct(
          Map[String,Statement](
          "a" -> Struct(
          Map[String, Statement]("b" -> Constant (4))
        ),
          "c" -> Constant(9)
        )
       ) ,
        Variable ("x")
      )

  val nestedStructAssignmentString = "x = {a:1, b:2, c:{d:1, e:2}};"
  val nestedStructAssignment =

      Assignment(
        Struct(Map[String, Statement](
          "a" -> Constant(1),
          "b" -> Constant(2),
          "c" -> Struct(Map[String, Statement](
            "d" -> Constant(1),
            "e" -> Constant(2)
          ))
        )),
        Variable("x")

    )


}
