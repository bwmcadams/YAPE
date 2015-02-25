
package net.evilmonkeylabs.yape

import org.parboiled.Rule
import org.pegdown.LinkRenderer.Rendering
import org.pegdown.Parser.ParseRunnerProvider
import org.pegdown.ast.{RefLinkNode, HeaderNode, RootNode}
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
class SlideHTMLRenderer(_linkRenderer: LinkRenderer = new YAPELinkRenderer,
                        _verbatimSerializers: Map[String, VerbatimSerializer] =
                          Map(VerbatimSerializer.DEFAULT -> DefaultVerbatimSerializer.INSTANCE),
                        _plugins: List[ToHtmlSerializerPlugin] = List.empty)
  extends ToHtmlSerializer(new YAPELinkRenderer, _verbatimSerializers.asJava, _plugins.asJava) {

  val ValidVideoExtensions = List("webm", "ogg", "mp4", "mov")

  override protected def printImageTag(rendering: LinkRenderer.Rendering) {
    // TODO - Audio support, but for now we assume a valid video extension (i.e. ogg which can be audio) is video, not audio
    if (rendering.href.endsWith(".webm") || rendering.href.endsWith(".mov") || rendering.href.endsWith(".mp4") ||
        rendering.href.endsWith(".mov")) {
      printer.print("<video")
      printAttribute("class", rendering.text)
      for (attr: LinkRenderer.Attribute <- rendering.attributes)
        printAttribute(attr.name, attr.value)
      printer.print(">")
      printer.print("<source")
      printAttribute("src", rendering.href)
      printer.print("/>")
      printer.print("</video>")
    } else {
      printer.print("<img")
      printAttribute("src", rendering.href)
      printAttribute("class", rendering.text)
      for (attr: LinkRenderer.Attribute <- rendering.attributes)
        printAttribute(attr.name, attr.value)
      printer.print("/>")
    }
  }



  override def visit(node: HeaderNode) {
    super.visit(node)
  }

  private def printAttribute(name: String, value: String) {
        printer.print(' ').print(name).print('=').print('"').print(value).print('"')
    }
}

object SlideHTMLRenderer {
  val options = Extensions.FENCED_CODE_BLOCKS | Extensions.SMARTYPANTS | Extensions.STRIKETHROUGH |
                 Extensions.HARDWRAPS | Extensions.AUTOLINKS
  val plugins = new PegDownPlugins.Builder().withInlinePluginRules(PragmaParser.HeaderFitPragma)
  // we need to find this pragma globally and will do so before we parse slides...
  // basicaslly ,putting footer and slidenumbers as a css element
  //.withBlockPluginRules(PragmaParser.ShowSlideNumbers).build()
  val parser = new PegDownProcessor(Extensions.ALL, plugins.build())
  def apply(markdown: String) = {
    try {
      val astRoot: RootNode = parser.parseMarkdown(markdown.toArray)
      val html = new SlideHTMLRenderer().toHtml(astRoot)
      // todo - presenter notes
      // sloppy post processing for header [fit] as it barfs inside the reflinks nodes
      var output = html
      for (n <- 1 to 6) {
        output = hFitReplacer(output, n)
      }
      output
    } catch {
      case e: ParsingTimeoutException =>
        println("PARSING TIMEOUT")
        null // todo - denull me
    }

  }

  def hFitReplacer(input: String, level: Int) = {
    input.replace(s"<h$level>[fit]", s"""<h$level class="fit">""")
  }
}

class YAPELinkRenderer extends LinkRenderer {


}
