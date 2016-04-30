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

   val simpleWhileString= "x = 2; y = 3; while (x) { y = y+x ; x = x - 1; }"


  val simpleWhile =
    Block(
      Assignment(
        Constant(10),
        Variable("x")


      ),
      Assignment(
        Constant(100),
        Variable("y")


      ),
      Loop(
        Variable("x"),
        Block(
          Assignment(
            Plus(
              Variable("y"),
              Variable("x")
            ),
            Variable("y")
          ),
          Assignment(
            Minus(
              Variable("x"),
              Constant(1)
            ),
            Variable("x")
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


  val complexStructWithAssign1String= "x= {a:{b:4}, c:9};"
  def complexStructWithAssign1Memory={
    val c = Cell(Num(9))
    val b = Cell(Num(4))
    val a = Cell(Ins(mutable.Map[String, Cell[_]]("b" -> b).asInstanceOf[Evaluator.Store]))
    val x = Cell(Ins(mutable.Map[String, Cell[_]]("a" -> a, "c" -> c).asInstanceOf[Evaluator.Store]))
    x
  }

  val complexStructWithAssign1=
    Assignment(
      Struct(
        Map[String, Statement](
          "a" -> Struct(
            Map[String, Statement](
              "b" -> Constant(4)
            )
          ),
          "c" -> Constant(9)
        )

      ),
      Variable("x")
    )

  val complexStructWithAssign2String = "x= {a:{b:4}, c:9}; y =x;"
  val complexStructWithAssign2Memory = complexStructWithAssign1Memory
  val complexStructWithAssign2 =
     Assignment(
        Variable("x"),
        Variable("y")
     )

  val complexStructWithAssign3String = "x= {a:{b:4}, c:9}; y.a.b=6;"
  val complexStructWithAssign3Memory = {
    val Cell_x = complexStructWithAssign1Memory
    val Cell_a = Cell_x.get.value.get("a").get
    val Cell_b= Cell_a.get.asInstanceOf[Ins].value.get("b").get
    Cell_b.set(Num(6))
    Cell_x
  }

  val complexStructWithAssign3 =
    Assignment(
      Constant(6),
      Variable("x"),
      Variable("a"),
      Variable("b")
    )


  val simpleSelectStr = "x.a;"

  val simpleSelect =
    Select(
      Variable("x"),
      Variable("a")
    )

  val selectWithStructStr = "{ a: 3 + 4, b: 5 + 6 }.a;"

  val selectWithStruct =
    Select(
      Struct(Map(
        "a" -> Plus(
          Constant(3),
          Constant(4)
        ),
        "b" -> Plus(
          Constant(5),
          Constant(6)
        )
      )),
      Variable("a")
    )

  val complexSelectStr = "list.tail.tail.head;"

  val complexSelect =
    Select(
      Variable("list"),
      Variable("tail"),
      Variable("tail"),
      Variable("head")
    )

  val undefinedSelectString = "x={}; x.a;"

  val undefinedFieldAccessSelectString = "x.a;"

  val undefinedFieldAssignValueToSelectString = "x.a = 3;"

  val accessSelectNumAsInsString = "x={a:1}; x.a.b;"
  val accessSelectNumAsIns =
    Assignment(
      Struct(
        Map[String, Statement](
          "a" -> Constant(1)
        )
      ),
      Variable("x")
    )
  Select(
    Variable("x"),
    Variable("a"),
    Variable("b")

  )

  val AssignToUndefinedSelectString = "x={a:{b:3}}; x.a.b.c.d = 4;"

  val AssignNumAsInsString = "x={z:5}; x.z.y = 1; x.z.y;"
  val AssignNumAsIns =
    Block(
     Assignment(
        Struct(
          Map[String, Statement](
            "z" -> Constant(5)
          )
        ),
        Variable("x")
      ),
      Assignment(
        Constant(1),
        Variable("x"),
        Variable("z"),
        Variable("y")
      ),
      Select(
        Variable("x"),
        Variable("z"),
        Variable("y")
      )
    )

  val AssignValueToUndefinedSelectString = "x={z:3}; x.w = 3;"

  AssignValueToUndefinedSelectString
//    Block(
//      Assignment(
//        Struct(
//          Map[String, Statement](
//            "z" -> Constant(3)
//          )
//        ),
//        Variable("x")
//      ),
//      Assignment(
//        Constant(3),
//        Variable("x"),
//        Variable("w")
//      )
//    )





}
