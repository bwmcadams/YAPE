
package net.evilmonkeylabs.yape

import java.io.{FileWriter, BufferedWriter, File}

import org.pegdown.ast.RootNode
import org.pegdown.{ParsingTimeoutException, LinkRenderer, Extensions, PegDownProcessor}
import scala.collection.JavaConversions._
import org.fusesource.scalate._

object MarkdownParser extends App {
  val outdir = "slides"

  val engine = new TemplateEngine
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


  var n = 1
  // todo - parse starting pragma for slide #s and footers
  for (raw <- slides) {
    val slide = SlideHTMLRenderer(raw)
    val output = engine.layout("src/main/resources/templates/base.ssp", Map("slide" -> slide))
    val file = new File(s"$outdir/$n.html")
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(output)
    bw.close()
    println(output)
    println("\n --------------- \n")
    n += 1
  }
}

