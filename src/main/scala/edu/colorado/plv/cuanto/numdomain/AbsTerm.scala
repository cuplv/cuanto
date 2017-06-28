package edu.colorado.plv.cuanto.numdomain

/**
  * Created by lumber on 4/20/17.
  */
abstract class AbsTerm {
  // the only "thing" that ApronLinTerm and ApronNonlinTerm share is that both of them represent a term.
  // TODO: Since Linterm0 and Texpr0Node do not share a common  parent class, the type here is Any. Although it is too un-restrictive.
  def term: Any
}
