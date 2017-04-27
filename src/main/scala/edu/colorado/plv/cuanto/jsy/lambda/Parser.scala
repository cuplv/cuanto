package edu.colorado.plv.cuanto.jsy.lambda

import edu.colorado.plv.cuanto.jsy.common.UnitOpParser
import edu.colorado.plv.cuanto.jsy.{functions, numerical}

object Parser extends UnitOpParser with functions.ParserLike with numerical.ParserWithBindingLike
