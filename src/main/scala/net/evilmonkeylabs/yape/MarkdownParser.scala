
package net.evilmonkeylabs.yape

import org.pegdown.ast.RootNode
import org.pegdown.{ParsingTimeoutException, LinkRenderer, Extensions, PegDownProcessor}
import scala.collection.JavaConversions._

object MarkdownParser extends App {
  val file = try {
    args(0)
  } catch {
    case t: Throwable =>
      throw new Exception("You must specify a file to parse", t)
  }
  val source = scala.io.Source.fromFile(file)
  val lines = source.mkString
  source.close()
  val slides = lines.split("\n---")
  println(s"# of slides: ${slides.length}")

  // todo - parse starting pragma for slide #s and footers
  for (slide <- slides) {
    println(SlideHTMLRenderer(slide))
    println("-------------------------------")
  }
}

