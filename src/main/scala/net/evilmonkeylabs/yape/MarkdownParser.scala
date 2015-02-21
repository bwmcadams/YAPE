
package net.evilmonkeylabs.yape

import org.pegdown.{LinkRenderer, Extensions, PegDownProcessor}

object MarkdownParser extends App {
  val parser = new PegDownProcessor(Extensions.FENCED_CODE_BLOCKS | Extensions.SMARTYPANTS | Extensions.STRIKETHROUGH |
                                    Extensions.HARDWRAPS | Extensions.AUTOLINKS )
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

  for (slide <- slides) {
    println(parser.markdownToHtml(slide))
    println("***********************************")
  }
}



