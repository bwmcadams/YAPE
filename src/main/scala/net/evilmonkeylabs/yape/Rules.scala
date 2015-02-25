package net.evilmonkeylabs.yape

import net.evilmonkeylabs.yape.ast.{FitThisHeaderNode, ShowSlideNumbersNode}
import org.parboiled.errors.{ErrorUtils, ParsingException}
import org.pegdown.ast.{Visitor, Node, AbstractNode}
import scala.collection.JavaConverters._
import scala.collection.JavaConversions._
import org.parboiled.scala._

trait Parsing extends Parser {
  def WhiteSpace = rule { zeroOrMore(anyOf(" \t")) }

  def Newline = rule { oneOrMore(anyOf("\n\r\f")) }
}

object PragmaParser extends Parsing {

  def ShowSlideNumbers = rule { "slidenumbers:" ~ WhiteSpace ~ "true" ~ push(ShowSlideNumbersNode) }

  def HeaderFitPragma = rule { "\\[fit\\]" ~ push(FitThisHeaderNode) }

  def fitHeader(expression: String) = {
    val parsingResult = ReportingParseRunner(HeaderFitPragma).run("### [fit]")
    parsingResult.result match {
      case Some(showSlides) => showSlides
      case None => throw new ParsingException("Invalid expression:\n" +
        ErrorUtils.printParseErrors(parsingResult))
    }
  }
  def showSlideNumbers(expression: String) = {
    val parsingResult = ReportingParseRunner(ShowSlideNumbers).run(expression)
    parsingResult.result match {
      case Some(showSlides) => showSlides
      case None => throw new ParsingException("Invalid expression:\n" +
                                              ErrorUtils.printParseErrors(parsingResult))
    }
  }
}

object FormulaParser extends Parsing {
  // NOTE: This should be handled automatically inline by MathJax

  //def FormulaBlock = rule {

  //def FormulaInline =
}




