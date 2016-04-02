package edu.luc.cs.laufer.cs473.expressions

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
    Variable("x"),
    Variable("y")
    //Constant(5)
  );

  val complexAssignmentString = "x = 5 ; y = 7;"

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
      Variable("x"),
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
    )

  val simpleConditionalString = "if (1) { x = 2; }"

  val simpleConditional =
    Conditional(
      Constant(1),
      Block(
        Assignment(
          Variable("x"),
          Constant(2)
        )
      )
    )

  val complexConditionalString = "if (1) { x = 2; } else { x = 3; }"

  val complexConditional =
    Conditional(
      Constant(1),
      Block(
        Assignment(
          Variable("x"),
          Constant(2)
        )
      ),
      Some(Block(
        Assignment(
          Variable("x"),
          Constant(3)
        )
      ))
    )

  val blockString = "{ r = r + x; y = y + 1 ; }"

  val blockFixture =
    Block(
      Assignment(
        Variable("r"),
        Plus(
          Variable("r"),
          Variable("x")
        )
      ),
      Assignment(
        Variable("y"),
        Plus(
          Variable("y"),
          Constant(1)
        )
      )
    )

  val complexConditional2String = "if (4) { r = r + x; y = y + 1; }"

  val complexConditional2 =
    Conditional(
      Constant(4),
      Block(
        Assignment(
          Variable("r"),
          Plus(
            Variable("r"),
            Variable("x")
          )
        ),
        Assignment(
          Variable("y"),
          Plus(
            Variable("y"),
            Constant(1)
          )
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
          Variable("r"),
          Plus(
            Variable("r"),
            Variable("x")
          )
        ),
        Assignment(
          Variable("y"),
          Minus(
            Variable("y"),
            Constant(1)
          )
        )
      )
    )



}
