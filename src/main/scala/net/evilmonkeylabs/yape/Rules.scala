package net.evilmonkeylabs.yape

import org.parboiled.errors.{ErrorUtils, ParsingException}
import org.pegdown.ast.{Visitor, Node, AbstractNode}
import scala.collection.JavaConverters._
import scala.collection.JavaConversions._
import org.parboiled.scala._


object PragmaParser extends Parser {


  def ShowSlideNumbers = rule { "slidenumbers:" ~ WhiteSpace ~ "true" ~ push(ShowSlideNumbersNode) }

  def WhiteSpace = rule { zeroOrMore(anyOf("\t ")) }

  def Newline = rule { oneOrMore(anyOf("\n\r\f")) }


  def showSlideNumbers(expression: String) = {
    val parsingResult = ReportingParseRunner(ShowSlideNumbers).run(expression)
    parsingResult.result match {
      case Some(showSlides) => showSlides
      case None => throw new ParsingException("Invalid expression:\n" +
                                              ErrorUtils.printParseErrors(parsingResult))
    }
  }
}

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

