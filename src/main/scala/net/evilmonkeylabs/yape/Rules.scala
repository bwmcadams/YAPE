package net.evilmonkeylabs.yape

import org.parboiled.{BaseParser, Rule}
import org.pegdown.{Extensions, Parser}
import org.pegdown.Parser._
import org.pegdown.plugins.BlockPluginParser

class PragmaParser extends Parser(SlideHTMLRenderer.options, 1000l, DefaultParseRunnerProvider) with BlockPluginParser {

  def blockPluginRules(): Array[Rule] = {
    Array(NodeSequence(slideNumbers()))
  }

  def slideNumbers(): Rule = {
    val content = StringBuilder.newBuilder
    Sequence(
      "slidenumbers",
      ":",
      whitespace(),
      OneOrMore(BaseParser.ANY, content += matchedChar()),
      push(content.result()),
      Newline()
    )
  }

  def whitespace(): Rule = {
    ZeroOrMore( AnyOf(" \t\f") )
  }
}
