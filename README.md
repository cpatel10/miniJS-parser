A front end (lexical analysis and syntax analysis) for a Scala-based interpreter/REPL for the simple imperative language MiniJS
Includes two types of parsers:

- [Scala parser combinators](http://www.scala-lang.org/api/current/scala-parser-combinators/#scala.util.parsing.combinator.Parsers)
- [parboiled2 parsing expression grammars](https://github.com/sirthias/parboiled2)

To run the tests:

      sbt test

To run minijs REPL:

      sbt run
      
      choose [1] CombinatorCalculator

To run either of the test examples:

      sbt test:run
   
To see unparser results run MainExtended
