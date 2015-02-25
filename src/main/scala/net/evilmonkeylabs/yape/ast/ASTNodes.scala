
package net.evilmonkeylabs.yape.ast

import org.pegdown.ast.{AbstractNode, Node, Visitor}

case object ShowSlideNumbersNode extends AbstractNode {

  println(s"********* Slide Numbers ENABLED")
  override def accept(visitor: Visitor) = {
    println(s"Visited by $visitor")
    visitor.visit(this: Node)
  }

  override def getChildren(): java.util.List[Node] = {
    null
  }
}

case object FitThisHeaderNode extends AbstractNode {

  println(s"********* Fit This Header Node ENABLED")
  override def accept(visitor: Visitor) = {
    println(s"Visited by $visitor")
    visitor.visit(this: Node)
  }

  override def getChildren(): java.util.List[Node] = {
    null
  }

}
