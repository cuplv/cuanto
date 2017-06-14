import edu.colorado.plv.cuanto._
import edu.colorado.plv.cuanto.jutil.implicits._
import edu.colorado.plv.cuanto.javita.ReturnFortytwoTest

val w = wala.Client(classOf[ReturnFortytwoTest].getRelativeURL.get)
val ir = w.makeIR(w.applicationClasses, { m => m.getSelector == wala.Client.MainSelector })
ir foreach { case (m,_) => println(m) }