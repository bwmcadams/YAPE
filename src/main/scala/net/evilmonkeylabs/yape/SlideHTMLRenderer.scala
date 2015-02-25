
package net.evilmonkeylabs.yape

import org.pegdown.ast.RootNode
import org.pegdown.plugins.{PegDownPlugins, ToHtmlSerializerPlugin}
import org.pegdown._

import scala.collection.JavaConverters._
import scala.collection.JavaConversions._
/**
 * A port of Pegdown's HTML Renderer focused on the necessary HTML rendering for
 * YAPE slides. Specifically, adds CSS Classes for certain types of data based on plugin feeds
 * and fixes a few other quirks such as stripping out Image ALT tags as contextual rather than mouseover data (
 * fit image, original size, autoplay, etc )
 */
class SlideHTMLRenderer(_linkRenderer: LinkRenderer = new LinkRenderer,
                        _verbatimSerializers: Map[String, VerbatimSerializer] =
                          Map(VerbatimSerializer.DEFAULT -> DefaultVerbatimSerializer.INSTANCE),
                        _plugins: List[ToHtmlSerializerPlugin] = List.empty)
  extends ToHtmlSerializer(_linkRenderer, _verbatimSerializers.asJava, _plugins.asJava) {

  override protected def printImageTag(rendering: LinkRenderer.Rendering) {
    printer.print("<img")
    printAttribute("src", rendering.href)
    printAttribute("class", rendering.text)
    for (attr: LinkRenderer.Attribute <- rendering.attributes)
      printAttribute(attr.name, attr.value)
    printer.print("/>")
  }

  private def printAttribute(name: String, value: String) {
        printer.print(' ').print(name).print('=').print('"').print(value).print('"')
    }
}

object SlideHTMLRenderer {
  val options = Extensions.FENCED_CODE_BLOCKS | Extensions.SMARTYPANTS | Extensions.STRIKETHROUGH |
                 Extensions.HARDWRAPS | Extensions.AUTOLINKS
  val plugins = new PegDownPlugins.Builder().build()
  // we need to find this pragma globally and will do so before we parse slides...
  // basicaslly ,putting footer and slidenumbers as a css element
  //.withBlockPluginRules(PragmaParser.ShowSlideNumbers).build()
  val parser = new PegDownProcessor(options, plugins)
  def apply(markdown: String) = {
    try {
      val astRoot: RootNode = parser.parseMarkdown(markdown.toArray)
      println(astRoot)
      new SlideHTMLRenderer().toHtml(astRoot)
    } catch {
      case e: ParsingTimeoutException =>
        println("PARSING TIMEOUT")
        null // todo - denull me
    }

  }
}
