import edu.colorado.plv.cuanto._
import scala.util.parsing.combinator.JavaTokenParsers

object ParserA extends JavaTokenParsers
val a = ParserA.parse(ParserA.floatingPointNumber, "3.14")

val MyParser = jsy.arithmetic.Parser
val b = MyParser.parse(MyParser.expr, "3.14")

val c = MyParser.parse("3-")
