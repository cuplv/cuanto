package edu.colorado.plv.cuanto.scoot.interpreter
package control

import soot.{Unit => SootUnit}
import soot.toolkits.graph.UnitGraph

import expression.Env

package object control {

  def succ(graph: UnitGraph)(env: Env, unit: SootUnit):
      Option[SootUnit] = ???

  def next[D](graph: UnitGraph, unit: SootUnit): Traversable[(Unit,D)] = ???

}
