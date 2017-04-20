package edu.colorado.plv.cuanto.jsy
package numerical

import edu.colorado.plv.cuanto.jsy.common.UnitOpParser

/**
  * @author Bor-Yuh Evan Chang
  */
trait ParserWithBindingLike extends ParserLike with binding.ParserLike {
  override def seqsub: Parser[Expr] = iteop
}

/**
  * @author Bor-Yuh Evan Chang
  */
object ParserWithBinding extends UnitOpParser with ParserWithBindingLike
