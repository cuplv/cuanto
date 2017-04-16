package edu.colorado.plv.cuanto.jsy
package primitives

import edu.colorado.plv.cuanto.jsy.common.UnitOpParser

/** The parser for JavaScripty with primitive values.
  *
  * Mixes the [[numerical.ParserWithBindingLike]] sub-language with the [[string.ParserLike]] sub-language.
  *
  * @author Bor-Yuh Evan Chang
  */
object Parser extends UnitOpParser with string.ParserLike with numerical.ParserWithBindingLike
