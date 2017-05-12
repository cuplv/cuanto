package edu.colorado.plv.cuanto.jsy.imp

import edu.colorado.plv.cuanto.jsy.common.UnitOpParser
import edu.colorado.plv.cuanto.jsy.{mutation, numerical}

object Parser extends UnitOpParser with mutation.ParserLike with numerical.ParserWithBindingLike
